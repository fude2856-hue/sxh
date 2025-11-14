package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.OperationLog;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.LogService;
import com.dz.campus_product_backend.service.UserService;
import com.dz.campus_product_backend.vo.ResultVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LogService log;

    @PostMapping("/register")
    public ResultVO register(@RequestBody User user) {
        ResultVO resultVO = userService.register(user);
        if(resultVO.getCode()==0){
            OperationLog operationLog = new OperationLog();
            User user1= userService.findByName(user.getUsername());
            operationLog.setUser_id(user1.getId());
            operationLog.setDetail("注册");
            operationLog.setCreate_time(String.valueOf(LocalDateTime.now()));
            ResultVO result=log.insertLog(operationLog);
            System.out.println(result);
        }
        return  resultVO;
    }

    @PostMapping("/login")
    public ResultVO login(@RequestBody Map<String, String> map, HttpServletRequest request) {
        User user = (User) userService.login(map.get("username"), map.get("password")).getData();
        if (user != null) {
            request.getSession().setAttribute("user", user);
            String id = user.getId();
            OperationLog operationLog = new OperationLog();
            operationLog.setUser_id(id);
            operationLog.setDetail("登录");
            operationLog.setCreate_time(String.valueOf(LocalDateTime.now()));
            ResultVO result=log.insertLog(operationLog);
            System.out.println(result);
        }
        return userService.login(map.get("username"), map.get("password"));
    }
    @PostMapping("/update")
    public ResultVO update(@RequestBody User user){
        return userService.updateUser(user);
    }
    @GetMapping("/information")
    public ResultVO getInfo(HttpServletRequest request){
        User loginUser=(User) request.getSession().getAttribute("user");
        if(loginUser!=null){
            return ResultVO.success(userService.findById(loginUser.getId()));
        }
        return ResultVO.error("未登录");
    }



    // 注销账号
    @DeleteMapping("/delete-account")
    public ResultVO deleteAccount(HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            return ResultVO.error("未登录");
        }
        userService.deleteUserById(Integer.parseInt(loginUser.getId()));
        return ResultVO.success("注销成功");
    }

    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("user");
        if (loginUser != null) {
            request.getSession().setAttribute("user", loginUser);
            String id = loginUser.getId();
            OperationLog operationLog = new OperationLog();
            operationLog.setUser_id(id);
            operationLog.setDetail("退出登录");
            operationLog.setCreate_time(String.valueOf(LocalDateTime.now()));
            ResultVO result=log.insertLog(operationLog);
            System.out.println(result);
        }
        return ResultVO.success("登出成功");
    }

    // 添加管理员接口
    @PostMapping("/users")
    public ResultVO addUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResultVO.success("添加成功");
        } catch (Exception e) {
            return ResultVO.error("添加失败");
        }
    }
}
