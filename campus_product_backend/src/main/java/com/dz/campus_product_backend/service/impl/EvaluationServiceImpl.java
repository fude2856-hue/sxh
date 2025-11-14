package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.EvaluationMapper;
import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.service.EvaluationService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationMapper evaluationMapper;
    @Override
    public ResultVO deleteEvaluation(String id, String UserId) {
            int i=evaluationMapper.delete(id,UserId);
            if(i>0){
                return ResultVO.success("评论删除成功");
            }
            else{
                return ResultVO.error("评论删除失败");
            }

    }

    @Override
    public ResultVO findMyEvaluation(String Userid) {
        List<Evaluation> evaluations=evaluationMapper.findAllByUserId(Userid);
        return ResultVO.success(evaluations);
    }

    @Override
    public ResultVO findEvaluationByProduct(String sellerId) {
        List<Evaluation> evaluations=evaluationMapper.findByProductId(sellerId);
        return ResultVO.success(evaluations);
    }



    // 查询所有评论
    @Override
    public List<Evaluation> findAllEvaluations() {
        return evaluationMapper.findAll();
    }

    // 根据评论ID查询评论
    @Override
    public Evaluation findEvaluationById(String evaluationId) {
        return evaluationMapper.findById(evaluationId);
    }

    // 根据用户ID查询评论
    @Override
    public List<Evaluation> findEvaluationsByUserId(String userId) {
        return evaluationMapper.findByUserId(userId);
    }

    // 根据商品ID查询评论
    @Override
    public List<Evaluation> findEvaluationsByProductId(String productId) {
        return evaluationMapper.findByProductId1(productId);
    }

    // 根据评论ID删除评论
    @Override
    public void deleteEvaluationById(String evaluationId) {
        evaluationMapper.deleteEvaluationById(evaluationId);
    }

    // 根据用户ID删除评论
    @Override
    public void deleteEvaluationByUserId(String userId) {
        evaluationMapper.deleteEvaluationByUserId(userId);
    }

    // 根据商品ID删除评论
    @Override
    public void deleteEvaluationByProductId(String productId) {
        evaluationMapper.deleteEvaluationByProductId(productId);
    }
}
