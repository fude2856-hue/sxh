package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.entity.Message;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.EvaluationService;
import com.dz.campus_product_backend.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;
    //查看所有评论
    @GetMapping("/all")
    public ResultVO selectAllEvaluation(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "3") int pageSize, HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultVO.error("未登录");
        }
        PageHelper.startPage(pageNum,pageSize);
        ResultVO resultVO= evaluationService.findMyEvaluation(user.getId());
        PageInfo<Evaluation> pageInfo=new PageInfo<>((List<Evaluation>)resultVO.getData());
        resultVO.setData(pageInfo);
        return resultVO;

    }
    //删除评论
    @DeleteMapping("/delete")
    public ResultVO deleteEvaluation(@Param("id") String id, HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("user");
        return evaluationService.deleteEvaluation(id, user.getId());
    }
//    @GetMapping("/comment")
//    public ResultVO getComments(HttpServletRequest request) {
//        User loginUser=(User) request.getSession().getAttribute("user");
//        return evaluationService.findEvaluationByProduct(loginUser.getId());
//    }
}
