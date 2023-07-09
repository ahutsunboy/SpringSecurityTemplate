package com.ahut.service.impl;

import com.ahut.config.RedisCache;
import com.ahut.pojo.User;
import com.ahut.service.LoginService;
import com.ahut.utils.JwtUtil;
import com.ahut.vo.LoginUser;
import com.ahut.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author : Scott Chen
 * @create 2023/7/9 13:16
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //校验用户
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw  new RuntimeException("用户名错误");
        }
        //获取用户对象id
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        //创建jwt存入响应类中返给前端,并将用户信息存入到redis中
        String jwt = JwtUtil.createJWT(userId);

        Map<String,String> data = new HashMap<>();
        data.put("token",jwt);

        redisCache.setCacheObject("login:" + userId,loginUser);
        return new ResponseResult(200,"登陆成功",data);
    }
}
