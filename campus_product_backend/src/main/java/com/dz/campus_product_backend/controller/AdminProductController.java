package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.dao.ProductMapper;
import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.service.ProductService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {
    @Autowired
    ProductService productService;
    // 查询所有商品
    @GetMapping("/findAll")
    public ResultVO findAllProducts() {
        List<Product> products = productService.findAllProducts();
        if (products != null && !products.isEmpty()) {
            return ResultVO.success(products);
        } else {
            return ResultVO.error("没有找到商品");
        }
    }

    // 根据商品ID查询商品
    @GetMapping("/findById")
    public ResultVO findProductById(@RequestParam int productId) {
        Product product = productService.findProductById(productId);
        if (product != null) {
            return ResultVO.success(product);
        } else {
            return ResultVO.error("商品不存在");
        }
    }

    // 根据商品名称查询商品
    @GetMapping("/findByName")
    public ResultVO findProductByName(@RequestParam String productName) {
        Product product = productService.findProductByName(productName);
        if (product != null) {
            return ResultVO.success(product);
        } else {
            return ResultVO.error("商品不存在");
        }
    }

    // 根据商品ID删除商品
    @DeleteMapping("/deleteById")
    public ResultVO deleteProductById(@RequestParam int productId) {
        productService.deleteProductById(productId);
        return ResultVO.success("商品删除成功");
    }

    // 根据商品名称删除商品
    @DeleteMapping("/deleteByName")
    public ResultVO deleteProductByName(@RequestParam String productName) {
        productService.deleteProductByName(productName);
        return ResultVO.success("商品删除成功");
    }
}
