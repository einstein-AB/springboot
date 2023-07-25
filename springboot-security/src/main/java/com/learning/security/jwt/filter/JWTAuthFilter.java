package com.learning.security.jwt.filter;

import com.learning.security.basic.service.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        //1. Extract header from request. API Request = Params + Authorization + Header + Body (refer Postman)
        String authorizationHeader = request.getHeader("Authorization");

        //Extract jwtToken from the Authorization Header
        String jwtToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }

        //2. Extract username and password from the token
        String username = getBody(jwtToken, getSecretKey()).getSubject();

        //3. (a)Validate and (b)Authenticate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        boolean isValidToken = (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken)); //(a)


        //Once token is validated, authenticate it from the DB //(b)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // If token is valid configure Spring Security to manually set authentication
            if (isValidToken) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // After setting the Authentication in the context, we specify that the current user is authenticated.
                // So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    //Extract body (payload) from API request
    private Claims getBody(String token, SecretKey secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    //Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getBody(token, getSecretKey()).getExpiration();
        return expiration.before(new Date(System.currentTimeMillis()));
    }

}
