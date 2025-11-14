package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.UserService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @GetMapping("/findAll")
    public ResultVO findAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResultVO.success(users);
    }

    // 根据id查询用户
    @GetMapping("/findById")
    public ResultVO findUserById(@RequestParam int id) {
        User user = userService.findUserById(String.valueOf(id));
        if (user != null) {
            return ResultVO.success(user);
        } else {
            return ResultVO.error("用户不存在");
        }
    }

    // 根据id删除用户
    @DeleteMapping("/deleteById")
    public ResultVO deleteUserById(@RequestParam int id) {
        userService.deleteUserById(id);
        return ResultVO.success("用户删除成功");
    }
//
//    // 根据用户名删除用户
//    @DeleteMapping("/deleteByUsername")
//    public ResultVO deleteUserByUsername(@RequestParam String username) {
//        userService.deleteUserByUsername(username);
//        return ResultVO.success("用户删除成功");
//    }
//
//    // 根据手机号删除用户
//    @DeleteMapping("/deleteByPhone")
//    public ResultVO deleteUserByPhone(@RequestParam String phone) {
//        userService.deleteUserByPhone(phone);
//        return ResultVO.success("用户删除成功");
//    }

    // 根据用户名查询
    @GetMapping("/findByUsername")
    public ResultVO findUserByUsername(@RequestParam String username) {
        User user = userService.findUserByUsername(username);
        if (user != null) {
            return ResultVO.success(user);
        } else {
            return ResultVO.error("用户不存在");
        }
    }

    // 根据手机号查询
    @GetMapping("/findByPhone")
    public ResultVO findUserByPhone(@RequestParam String phone) {
        User user = userService.findUserByPhone(phone);
        if (user != null) {
            return ResultVO.success(user);
        } else {
            return ResultVO.error("用户不存在");
        }
    }

}
