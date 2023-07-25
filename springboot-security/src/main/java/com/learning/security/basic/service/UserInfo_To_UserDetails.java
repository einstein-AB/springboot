package com.learning.security.basic.service;

import com.learning.security.basic.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfo_To_UserDetails implements UserDetails {

    //Standard fields that will be needed to create UserDetail
    //Refer Hardcoded UserDetail creation of 2 user in `SecurityConfig` class.
    //`SecurityConfig` class is used to create a bean of `UserDetailsService`
    //`UserDetailsService` is used to create UserDetail ie; custom user with username, password, role
    private String username;
    private String password;
    List<GrantedAuthority> grantedAuthorities;


    //Customizing the constructor
    //Converting UserInfo --> UserDetail
    public UserInfo_To_UserDetails(UserInfo userInfo) {
        username = userInfo.getUsername();
        password = userInfo.getPassword();
        grantedAuthorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
