package com.ahut.controller;

import com.ahut.pojo.User;
import com.ahut.service.LogoutService;
import com.ahut.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : Scott Chen
 * @create 2023/7/9 14:30
 */

@RestController
@RequestMapping("/user")
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseResult logout(@RequestBody User user){
        return logoutService.logout(user);
    }
}
