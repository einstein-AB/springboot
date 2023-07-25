package com.learning.security.jwt.service;

import com.learning.security.basic.entity.UserInfo;
import com.learning.security.basic.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<UserInfo> findAllUserInfo() {
        return userInfoRepository.findAll();
    }

    public UserInfo findUserInfoById(String userId) {
        return userInfoRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new UsernameNotFoundException("user with mentioned ID not found"));
    }

    public UserInfo saveUserInfo(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword())); //encoding password before saving to DB
        return userInfoRepository.save(userInfo);
    }



}
