package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> getCategories();
    //设置所有种类的is_use
    int updateall();
    //设置单个种类的is_use
    int enableCategory(String id);
    //查询已经启用的类
    Category selectEnable();
}
