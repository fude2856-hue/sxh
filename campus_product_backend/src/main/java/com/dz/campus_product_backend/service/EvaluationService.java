package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.vo.ResultVO;

import java.util.List;

public interface EvaluationService {
    //删除我的评论
    ResultVO deleteEvaluation(String id, String UserId);
    //查看我发出的评论
    ResultVO findMyEvaluation(String Userid);
    ResultVO findEvaluationByProduct(String sellerId);

    // 查询所有评论
    List<Evaluation> findAllEvaluations();

    // 根据评论ID查询评论
    Evaluation findEvaluationById(String evaluationId);

    // 根据用户ID查询评论
    List<Evaluation> findEvaluationsByUserId(String userId);

    // 根据商品ID查询评论
    List<Evaluation> findEvaluationsByProductId(String productId);

    // 根据评论ID删除评论
    void deleteEvaluationById(String evaluationId);

    // 根据用户ID删除评论
    void deleteEvaluationByUserId(String userId);

    // 根据商品ID删除评论
    void deleteEvaluationByProductId(String productId);
}

