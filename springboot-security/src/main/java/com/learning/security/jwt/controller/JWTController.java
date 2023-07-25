package com.learning.security.jwt.controller;

import com.learning.security.jwt.dto.AuthRequest;
import com.learning.security.jwt.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/jwt")
public class JWTController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping ("/token")
    public String authenticateAndGetJWTToken (@RequestBody AuthRequest authRequest) {

        //Setup `Authentication` of user trying to get token before returning JWT
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));

        //Authenticate and provide JWT only to those users who are registered. ie; have a valid username password
        //ie; Authenticate user for the first time and then send away the application's token
        if (authentication.isAuthenticated()) {
            //Generate token for given username
            return jwtService.generateJwtToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Credentials provided are invalid. JWT cannot be generated for invalid users");
        }
    }


}
