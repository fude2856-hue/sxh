package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.vo.ResultVO;


public interface CommentService {
    //发表评论
    ResultVO createComment(Evaluation comment);
}
