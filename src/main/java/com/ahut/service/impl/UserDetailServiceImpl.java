package com.ahut.service.impl;

import com.ahut.mapper.UserMapper;
import com.ahut.pojo.User;
import com.ahut.vo.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author : Scott Chen
 * @create 2023/7/9 10:51
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //工具用户名查村用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(wrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名不存在");
        }

        //查询权限信息
        List<String> authorities = userMapper.getAuthoritiesByUserId(user.getUserId());


        return new LoginUser(user,authorities);
    }
}
