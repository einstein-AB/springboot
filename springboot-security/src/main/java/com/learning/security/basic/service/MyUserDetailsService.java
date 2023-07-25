package com.learning.security.basic.service;

import com.learning.security.basic.entity.UserInfo;
import com.learning.security.basic.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //retrieve user info for matching username
        Optional<UserInfo> userInfoFromDB = userInfoRepository.findByUsername(username);

        //Map [UserInfo] --> [UserDetails]
        UserInfo_To_UserDetails userDetails = userInfoFromDB.map(userInfoDB -> new UserInfo_To_UserDetails(userInfoDB))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in DB with username as :" + username));

        return userDetails;
    }
}
