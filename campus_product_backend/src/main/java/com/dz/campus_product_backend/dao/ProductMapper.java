package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    int insertProduct(Product product);
    List<Product> selectByCategoryId(String categoryId);
    List<Product> selectAllProducts();
    //根据商品名进行模糊查询
    List<Product> selectByProductName(String productName);
    //根据商品id查询商品
    Product selectByProductId(String productId);
    //根据商品id删除商品
    int deleteByProductId(String productId);
    //更新商品库存
    int updateStock(String productId);

    // 根据商品名称查找商品
    Product findByProductName(String productName);

    // 根据商品ID查找商品
    Product findByProductId(int productId);

    // 查询所有商品
    List<Product> findAll();

    // 根据商品ID删除商品
    int deleteProductById(int productId);

    // 根据商品名称删除商品
    int deleteProductByName(String productName);
}
