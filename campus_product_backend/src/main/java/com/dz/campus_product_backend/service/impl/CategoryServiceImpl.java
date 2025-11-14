package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.CategoryMapper;
import com.dz.campus_product_backend.entity.Category;
import com.dz.campus_product_backend.service.CategoryService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultVO listCategories() {
        return ResultVO.success(categoryMapper.getCategories());
    }

    @Override
    public ResultVO useCategory(String id) {
        //确保所有类别的is_use都为0
        int result1=categoryMapper.updateall();
        //设置指定类别的is_use为1
        int result2=categoryMapper.enableCategory(id);
        if(result1==result2){
            return ResultVO.success("分类成功启用");
        }
        else{
            return ResultVO.error("分类未启用");
        }
    }
    @Override
    public Category enableCategory(){
        return categoryMapper.selectEnable();
    }
}
