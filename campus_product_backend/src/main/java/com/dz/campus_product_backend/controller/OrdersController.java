package com.dz.campus_product_backend.controller;



import com.dz.campus_product_backend.entity.Interaction;
import com.dz.campus_product_backend.entity.Orders;
import com.dz.campus_product_backend.service.OrdersService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/getPage")
    public ResultVO getOrdersByPage(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String keyword) {
        List<Orders> orders = ordersService.getOrdersByPage(pageNum, pageSize, keyword);
        int total = ordersService.getTotalCount(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);

        return ResultVO.success(result);
    }
    //增加
    @PostMapping("/insert")
    public ResultVO insertOrders(@RequestBody Orders orders) {
        ordersService.insertOrders(orders);
        return ResultVO.success(orders);
    }
    //根据id修改
    @PutMapping("/update")
    public ResultVO updateOrders(@RequestBody Orders orders) {
        ordersService.updateOrdersById(orders);
        return ResultVO.success(orders);
    }
    //删除
    @DeleteMapping("/delete/{id}")
    public ResultVO deleteOrdersById(@PathVariable String id) {
        ordersService.deleteOrdersById(id);
        return ResultVO.success(id);

    }
    //批量删除
    @DeleteMapping("/delete")
    public ResultVO deleteOrdersByIds(@RequestBody List<String> ids) {
        ordersService.deleteOrdersByIds(ids);
        return ResultVO.success(ids);
    }
}
