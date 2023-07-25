package com.learning.security.basic.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/security")
public class SecurityController {

    @GetMapping ("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //For single role authentication
    public String adminRole () {
        return "hello ADMIN";
    }

    @GetMapping ("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')") //For multiple role authentication
    public String userRole () {
        return "hello Registered USER";
    }

    @GetMapping ("/all")
    public String allTheGuest () {
        return "hello guest";
    }
}
