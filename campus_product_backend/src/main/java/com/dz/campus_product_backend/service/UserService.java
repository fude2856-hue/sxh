package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    ResultVO register(User user);
    ResultVO login(String username, String password);
    ResultVO updateUser(User user);
    User findById(String id);
    User findByName(String name);
    User findUserById(String id);
    //根据手机号查询用户
    ResultVO getUserByPhone(String phone);
    //根据用户名查询用户
    ResultVO getUserByUsername(String username);

    //根据id删除用户
    void deleteUserById(int id);

    //根据id删除用户
    ResultVO deleteUser(int id);

    //查询所有用户
    ResultVO getAllUsers();

    //查询所有用户（返回list）
    List<User> findAllUsers();

    User findUserByUsername(String username);
    User findUserByPhone(String phone);

    void createUser(User user);
}
