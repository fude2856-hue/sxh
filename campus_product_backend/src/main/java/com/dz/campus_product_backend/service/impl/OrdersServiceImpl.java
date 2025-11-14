package com.dz.campus_product_backend.service.impl;
import com.dz.campus_product_backend.dao.OrdersMapper;
import com.dz.campus_product_backend.dao.UserMapper;
import com.dz.campus_product_backend.entity.Orders;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.OrdersService;
import com.dz.campus_product_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public int getTotalCount(String keyword) {
        return ordersMapper.getTotalCount(keyword);
    }

    @Override
    public List<Orders> getOrdersByPage(int pageNum, int pageSize, String keyword) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        int offset = (pageNum - 1) * pageSize;
        return ordersMapper.getOrdersByPage(pageNum, pageSize, keyword, offset);
    }

    @Override
    public int insertOrders(Orders orders) {
        return ordersMapper.insertOrders(orders);
    }


@Override
public int updateOrdersById(Orders orders) {
    return ordersMapper.updateOrdersById(orders);

}

@Override
public int deleteOrdersById(String id) {
    return ordersMapper.deleteOrdersById(id);

}

    @Override
    public int deleteOrdersByIds(List<String> ids) {
        return ordersMapper.deleteOrdersByIds(ids);

    }

}
