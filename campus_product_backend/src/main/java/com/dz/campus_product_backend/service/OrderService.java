package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Orders;
import com.dz.campus_product_backend.vo.ResultVO;

public interface OrderService {
    ResultVO createOrder(String user_id,String product_id);
}
