package com.learning.security.basic.config;

import com.learning.security.basic.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //Authentication
    @Bean
    public UserDetailsService userDetailsService () {

        /* ------------------Hardcoded UserDetails / InMemory UserDetails------------------
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .build();
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .authorities("ROLE_USER")
                .build();
        return  new InMemoryUserDetailsManager(admin, manager, user);
        */

        return new MyUserDetailsService();
    }

    //Authorization
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                //----------------------------------basic security------------------------------------------------------
                .authorizeHttpRequests().requestMatchers("/security/all").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/security/**").authenticated().and().formLogin()


                //----------------------------------JWT token------------------------------------------------------
                .and()
                .authorizeHttpRequests().requestMatchers("/jwt/token").permitAll()


                //----------------------------------user related APIs------------------------------------------------------
                .and()
                .authorizeHttpRequests().requestMatchers("/user/save").permitAll()

                .and()
                .authorizeHttpRequests().requestMatchers("/user/**").authenticated().and().formLogin()
                .and().build();
    }


    /*
        If using custom Authentication from DB, then the `AuthenticationProvider` bean needs to be provided...
        with custom UserDetailsService and PasswordEncoder bean
    */
    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    //This bean is needed to encode password.
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }


    /*
        This bean is used in JWTController for authenticating user before returning JWT token.
        This bean allows us to authenticate credentials anywhere within the app.
    */
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


}
