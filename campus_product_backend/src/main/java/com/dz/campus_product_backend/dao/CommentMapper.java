package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Evaluation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    int insertComment(Evaluation comment);
}
