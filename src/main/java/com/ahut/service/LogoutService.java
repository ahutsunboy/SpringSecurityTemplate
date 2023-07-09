package com.ahut.service;

import com.ahut.pojo.User;
import com.ahut.vo.ResponseResult;

/**
 * @Author : Scott Chen
 * @create 2023/7/9 14:33
 */
public interface LogoutService {
    ResponseResult logout(User user);
}
