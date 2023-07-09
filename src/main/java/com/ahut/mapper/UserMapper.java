package com.ahut.mapper;

import com.ahut.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86187
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-07-09 10:30:41
* @Entity com.ahut.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {

    List<String> getAuthoritiesByUserId(@Param("userId") Integer userId);
}




