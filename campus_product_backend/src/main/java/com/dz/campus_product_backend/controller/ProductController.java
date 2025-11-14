package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Category;
import com.dz.campus_product_backend.entity.Product;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.CategoryService;
import com.dz.campus_product_backend.service.ProductService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    //发布商品
    @PostMapping("/createproduct")
    public ResultVO createProduct(@RequestBody Product product, HttpSession session){
        product.setCreate_time(String.valueOf(LocalDateTime.now()));
        //获得当前登录者的id
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResultVO.error("未登录，无法发布商品");
        }
        product.setUser_id(currentUser.getId());
        return ResultVO.success(productService.createProduct(product));
    }
    //获取属于启用类的商品
    @GetMapping("enabled-category")
    public ResultVO getProductsByEnableCategory(){
        //查询is_use为1的种类
        Category category=categoryService.enableCategory();
        String id=category.getId();
        return productService.allEnabledProducts(id);
    }
    //获取所有商品
    @GetMapping("/all")
    public ResultVO getProductList(@RequestParam String currentPage, @RequestParam String pageSize) {
        int currentpage=Integer.parseInt(currentPage);
        int pagesize=Integer.parseInt(pageSize);
        return productService.paginatedProducts(currentpage, pagesize);
    }

    //按商品名进行模糊查询
    @GetMapping("/search")
    public ResultVO searchProducts(@Param("name") String name){
        return productService.searchProducts(name);
    }
    //根据商品id查询商品
    @GetMapping("/detail")
    public ResultVO getProductById(@Param("id") String id){
        return productService.getProductById(id);
    }
}
