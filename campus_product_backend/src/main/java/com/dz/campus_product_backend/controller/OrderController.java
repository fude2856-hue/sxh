package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Orders;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.OrderService;
import com.dz.campus_product_backend.vo.ResultVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/buy")
    //接收前端传来的ID
    public ResultVO buy(@RequestBody Map<String, String> map, HttpServletRequest request) {
        User loginUser=(User) request.getSession().getAttribute("user");
        String userId=loginUser.getId();
        String productId=map.get("productId");
        return orderService.createOrder(userId,productId);

    }
}
