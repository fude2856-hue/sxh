package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.vo.ResultVO;

import java.util.List;

public interface ProductService {
    ResultVO createProduct(Product product);

    ResultVO allEnabledProducts(String id);
    //根据商品名进行的模糊查询
    ResultVO searchProducts(String name);
    //根据商品id查询商品
    ResultVO getProductById(String id);
    ResultVO paginatedProducts(int currentPage, int pageSize);

    // 查询所有商品
    List<Product> findAllProducts();

    // 根据商品 ID 查询商品
    Product findProductById(int productId);

    // 根据商品名称查询商品
    Product findProductByName(String productName);

    // 根据商品 ID 删除商品
    void deleteProductById(int productId);

    // 根据商品名称删除商品
    void deleteProductByName(String productName);


}
