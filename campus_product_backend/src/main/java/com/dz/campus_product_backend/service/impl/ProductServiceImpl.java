package com.dz.campus_product_backend.service.impl;


import com.dz.campus_product_backend.dao.ProductMapper;
import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.service.ProductService;
import com.dz.campus_product_backend.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResultVO createProduct(Product product) {
        return ResultVO.success(productMapper.insertProduct(product));
    }
    @Override
    public ResultVO allEnabledProducts(String id) {
        return ResultVO.success(productMapper.selectByCategoryId(id));
    }

    @Override
    public ResultVO searchProducts(String productName) {
        return ResultVO.success(productMapper.selectByProductName(productName));
    }
    @Override
    public ResultVO getProductById(String id) {
        return ResultVO.success(productMapper.selectByProductId(id));
    }
    @Override
    public ResultVO paginatedProducts(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Product> products = productMapper.selectAllProducts();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return ResultVO.success(pageInfo);
    }

    // 查询所有商品
    @Override
    public List<Product> findAllProducts() {
        return productMapper.findAll();
    }

    // 根据商品 ID 查询商品
    @Override
    public Product findProductById(int productId) {
        return productMapper.findByProductId(productId);
    }

    // 根据商品名称查询商品
    @Override
    public Product findProductByName(String productName) {
        return productMapper.findByProductName(productName);
    }

    // 根据商品 ID 删除商品
    @Override
    public void deleteProductById(int productId) {
        productMapper.deleteProductById(productId);
    }

    // 根据商品名称删除商品
    @Override
    public void deleteProductByName(String productName) {
        productMapper.deleteProductByName(productName);
    }

}
