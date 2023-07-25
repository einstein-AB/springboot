package com.learning.security.jwt.controller;

import com.learning.security.basic.entity.UserInfo;
import com.learning.security.jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping ("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserInfo> findAllUserInfo () {
        return userInfoService.findAllUserInfo();
    }

    @GetMapping ("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserInfo findUserInfoById (@PathVariable String userId) {
        return userInfoService.findUserInfoById(userId);
    }

    @PostMapping("/save")
    public UserInfo saveUserInfo (@RequestBody UserInfo userInfo) {
        return userInfoService.saveUserInfo(userInfo);
    }
}
