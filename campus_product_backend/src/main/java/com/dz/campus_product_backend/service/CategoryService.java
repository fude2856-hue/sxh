package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Category;
import com.dz.campus_product_backend.vo.ResultVO;

public interface CategoryService {
    ResultVO listCategories();
    ResultVO useCategory(String id);
    Category enableCategory();
}
