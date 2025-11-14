package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.service.EvaluationService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/evaluation")
public class AdminEvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    // 查询所有评论
    @GetMapping("/findAll")
    public ResultVO findAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.findAllEvaluations();
        if (evaluations != null && !evaluations.isEmpty()) {
            return ResultVO.success(evaluations);
        } else {
            return ResultVO.error("没有找到评论");
        }
    }

    // 根据评论ID查询评论
    @GetMapping("/findById")
    public ResultVO findEvaluationById(@RequestParam String evaluationId) {
        Evaluation evaluation = evaluationService.findEvaluationById(evaluationId);
        if (evaluation != null) {
            return ResultVO.success(evaluation);
        } else {
            return ResultVO.error("评论不存在");
        }
    }

    // 根据用户ID查询评论
    @GetMapping("/findByUserId")
    public ResultVO findEvaluationsByUserId(@RequestParam String userId) {
        List<Evaluation> evaluations = evaluationService.findEvaluationsByUserId(userId);
        if (evaluations != null && !evaluations.isEmpty()) {
            return ResultVO.success(evaluations);
        } else {
            return ResultVO.error("没有找到评论");
        }
    }

    // 根据商品ID查询评论
    @GetMapping("/findByProductId")
    public ResultVO findEvaluationsByProductId(@RequestParam String productId) {
        List<Evaluation> evaluations = evaluationService.findEvaluationsByProductId(productId);
        if (evaluations != null && !evaluations.isEmpty()) {
            return ResultVO.success(evaluations);
        } else {
            return ResultVO.error("没有找到评论");
        }
    }

    // 根据评论ID删除评论
    @DeleteMapping("/deleteById")
    public ResultVO deleteEvaluationById(@RequestParam String evaluationId) {
        evaluationService.deleteEvaluationById(evaluationId);
        return ResultVO.success("评论删除成功");
    }

    // 根据用户ID删除评论
    @DeleteMapping("/deleteByUserId")
    public ResultVO deleteEvaluationByUserId(@RequestParam String userId) {
        evaluationService.deleteEvaluationByUserId(userId);
        return ResultVO.success("评论删除成功");
    }

    // 根据商品ID删除评论
    @DeleteMapping("/deleteByProductId")
    public ResultVO deleteEvaluationByProductId(@RequestParam String productId) {
        evaluationService.deleteEvaluationByProductId(productId);
        return ResultVO.success("评论删除成功");
    }
}
