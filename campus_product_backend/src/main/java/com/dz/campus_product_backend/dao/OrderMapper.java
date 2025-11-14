package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int insertOrder(Orders order);
}
