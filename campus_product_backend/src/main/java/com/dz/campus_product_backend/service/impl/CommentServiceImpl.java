package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.CommentMapper;
import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.service.CommentService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public ResultVO createComment(Evaluation comment) {
        int result=commentMapper.insertComment(comment);
        if(result>0){
            return ResultVO.success("评论成功");
        }
        return ResultVO.error("评论失败");
    }
}
