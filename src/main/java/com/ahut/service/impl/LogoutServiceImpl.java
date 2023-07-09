package com.ahut.service.impl;

import com.ahut.config.RedisCache;
import com.ahut.pojo.User;
import com.ahut.service.LogoutService;
import com.ahut.vo.LoginUser;
import com.ahut.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Author : Scott Chen
 * @create 2023/7/9 14:34
 */

@Service
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult logout(User user) {
        //获取Authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取安全用户类
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        //删除redis中的用户信息
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200,"退出成功");
    }
}
