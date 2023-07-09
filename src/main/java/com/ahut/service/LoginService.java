package com.ahut.service;

import com.ahut.pojo.User;
import com.ahut.vo.ResponseResult;

/**
 * @Author : Scott Chen
 * @create 2023/7/9 13:15
 */
public interface LoginService {
    ResponseResult login(User user);
}
