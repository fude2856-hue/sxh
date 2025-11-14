package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 插入用户信息
    int insertUser(User user);
    //根据用户名查找用户
    User findByUsername(String username);
    //根据手机号查找用户
    User findByPhone(String phone);
    //登录功能
    User login(@Param("username") String username, @Param("password") String password);
    User findById(String id);
    void updateUser(User user);
    //查询所有用户
    List<User> findAll();
    void setUserAccountStatusById(@Param("id") int id, @Param("status") String status);

    User findUserByUsername(String username);
    User findUserByPhone(String phone);
}
