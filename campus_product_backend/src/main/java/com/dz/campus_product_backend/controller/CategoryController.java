package com.dz.campus_product_backend.controller;


import com.dz.campus_product_backend.service.CategoryService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){
        return categoryService.listCategories();
    }

    @PostMapping("/use")
    public ResultVO use(@RequestBody Map<String,Object> map){
        String category_id=map.get("categoryId").toString();
        //设置is_use进行启用
        return categoryService.useCategory(category_id);
    }
}
