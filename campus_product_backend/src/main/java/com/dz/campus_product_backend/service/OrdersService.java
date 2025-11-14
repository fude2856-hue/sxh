package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.entity.Orders;

import java.util.List;

public interface OrdersService {

    public int getTotalCount(String keyword) ;

    List<Orders> getOrdersByPage(int pageNum, int pageSize, String keyword);
    int insertOrders(Orders orders);
    int updateOrdersById(Orders orders);
    int deleteOrdersById(String id);
    //批量删除
    int deleteOrdersByIds(List<String> ids);


}

