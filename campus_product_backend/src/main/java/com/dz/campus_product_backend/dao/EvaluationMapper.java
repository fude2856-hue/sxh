package com.dz.campus_product_backend.dao;
import com.dz.campus_product_backend.entity.Evaluation;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface EvaluationMapper {
    //按商品id查找评论
    List<Evaluation> findByProductId(String sellerId);
    //删除评论
    int delete(@Param("id") String id,@Param("user_id") String user_id);
    //查看所有评论
    List<Evaluation> findAllByUserId(@Param("user_id") String user_id);


    // 插入评论
    int insertEvaluation(Evaluation evaluation);

    // 根据评论ID查找评论
    Evaluation findByEvaluationId(String evaluationId);

    // 根据用户ID查找评论
    List<Evaluation> findByUserId(String userId);

    // 根据商品ID查找评论
    List<Evaluation> findByProductId1(String productId);

    // 查询所有评论
    List<Evaluation> findAll();

    // 根据评论ID删除评论
    int deleteEvaluationById(String evaluationId);

    // 根据用户ID删除评论
    int deleteEvaluationByUserId(String userId);

    // 根据商品ID删除评论
    int deleteEvaluationByProductId(String productId);

    Evaluation findById(String evaluationId);

}
