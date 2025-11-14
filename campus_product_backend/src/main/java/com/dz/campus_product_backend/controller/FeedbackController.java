package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.Message;
import com.dz.campus_product_backend.service.FeedbackService;
import com.dz.campus_product_backend.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @GetMapping("/all")
    public ResultVO getAllFeedback(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        ResultVO resultVO=feedbackService.getFeedback("1");
        PageInfo<Message> pageInfo=new PageInfo<>((List<Message>)resultVO.getData());
        resultVO.setData(pageInfo);
        return resultVO;
    }
    @PostMapping("/reply")
    public ResultVO Reply(@RequestBody Map<String,Object> requestBody){
        String content=(String) requestBody.get("content");
        String feedbackId=(String) requestBody.get("feedbackId");
        return feedbackService.addReplyToFeedback(feedbackId,content);
    }
}
