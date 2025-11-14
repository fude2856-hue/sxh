package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Interaction;
import com.dz.campus_product_backend.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdersMapper {
    //分页加模糊查询
    List<Orders> getOrdersByPage(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("keyword") String keyword,
            @Param("offset") int offset
    );

    int getTotalCount(String keyword);
    //增加
    int insertOrders(Orders orders) ;
    //根据id修改
    int updateOrdersById(Orders orders);
    //删除
    int deleteOrdersById(String id);
    //批量删除
    int deleteOrdersByIds(List<String> ids);

}


