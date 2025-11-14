package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.entity.User;
import com.dz.campus_product_backend.service.CommentService;
import com.dz.campus_product_backend.vo.ResultVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    //发表评论
    @PostMapping("/add")
    public ResultVO addComment(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String productId = map.get("productId").toString();
        String score = map.get("score").toString();
        String content = map.get("content").toString();
        Evaluation comment = new Evaluation();
        comment.setProduct_id(productId);
        comment.setContent(content);
        comment.setScore(score);
        comment.setCreate_time(String.valueOf(LocalDateTime.now()));
        User loginUser=(User) request.getSession().getAttribute("user");
        comment.setUser_id(loginUser.getId());
        return commentService.createComment(comment);
    }
}
