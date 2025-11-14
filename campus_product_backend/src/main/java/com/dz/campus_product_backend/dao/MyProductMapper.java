package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface MyProductMapper {
    // 分页加条件模糊查询我的商品
    List<Product> getMyProductsByPage(@Param("userId") int userId, @Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize);


    int getMyProductsTotal(@Param("userId") int userId, @Param("keyword") String keyword);

    int updateMyProduct(Product product);
    int deleteById( String productId);
    //批量删除
    int deleteByIds(List<String> ids);
}