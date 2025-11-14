package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.vo.ResultVO;

import java.util.List;
import java.util.Map;

public interface MyProductService {
    ResultVO getMyProductsByPage(int userId, String keyword, int pageNum, int pageSize);
    // 分页加条件模糊查询我的商品

    ResultVO updateMyProduct(Product product);
    int deleteMyProductById(String productId);
    //批量删除
    int deleteByIds(List<String> ids);

}