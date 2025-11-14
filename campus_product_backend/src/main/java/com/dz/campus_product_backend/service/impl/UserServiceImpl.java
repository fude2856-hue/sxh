package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.UserMapper;
import com.dz.campus_product_backend.entity.OperationLog;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.UserService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //注册功能
    @Override
    public ResultVO register(User user) {
        if(userMapper.findByUsername(user.getUsername()) != null){
            return ResultVO.error("用户名已存在");
        }
        user.setRole("2");
        user.setCreate_time(String.valueOf(LocalDateTime.now()));
        //默认账号状态为1，正常用户
        user.setAccount_status("1");
        userMapper.insertUser(user);
        return ResultVO.success("注册成功");
    }
    //登录功能
    @Override
    public ResultVO login(String username,String password) {
        User user = userMapper.login(username,password);
        if(user!=null){
            if(user.getAccount_status().equals("1")){
                return ResultVO.success(user);
            }
            else{
                return ResultVO.error("账号状态异常，已禁用");
            }
        }
        else{
            return ResultVO.error("用户名或密码错误");
        }
    }
    @Override
    public ResultVO updateUser(User user){
        if(user.getUsername().equals("admin"))
        {
            return ResultVO.error("此用户名不可使用");
        }
        User old=userMapper.findById(user.getId());
        if(old == null)
            return ResultVO.error("用户不存在");
        if(user.getPassword()==null||user.getPassword().isEmpty()){
            //如果更新密码为空，保持不变
            user.setPassword(old.getPassword());
        }
        user.setCreate_time(String.valueOf(String.valueOf(LocalDateTime.now())));
        userMapper.updateUser(user);
        return ResultVO.success("更新成功");

    }

    @Override
    public User findById(String id) {
        return userMapper.findById(id);
    }
    @Override
    public User findByName(String username) {
        return userMapper.findByUsername(username);
    }
    @Override
    public User findUserById(String id) {
        return userMapper.findById(id);  // 假设UserMapper中的findById方法也接收String类型
    }



    @Override
    public ResultVO getUserByPhone(String phone) {
        User user = userMapper.findByPhone(phone);
        return user != null ? ResultVO.success(user) : ResultVO.error("用户不存在");
    }

    @Override
    public ResultVO getUserByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user != null ? ResultVO.success(user) : ResultVO.error("用户不存在");
    }

    @Override
    public ResultVO getAllUsers() {
        List<User> users = userMapper.findAll();
        if (users.isEmpty()) {
            return ResultVO.error("没有找到用户");
        } else {
            return ResultVO.success(users);
        }
    }
    //查询所有用户（返回list）
    @Override
    public List<User> findAllUsers() {
        return userMapper.findAll();
    }
    @Override
    public void deleteUserById(int id) {
        userMapper.setUserAccountStatusById(id, "2");
    }

    @Override
    public ResultVO deleteUser(int id) {
        return null;
    }
    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public User findUserByPhone(String phone) {
        return userMapper.findUserByPhone(phone);
    }

    // 创建管理员
    @Override
    public void createUser(User user) {
        if(userMapper.findByUsername(user.getUsername()) != null){
            throw new RuntimeException("用户名已存在");
        }
        user.setRole("1");
        user.setCreate_time(String.valueOf(LocalDateTime.now()));
        user.setAccount_status("1");
        userMapper.insertUser(user);
    }
}
