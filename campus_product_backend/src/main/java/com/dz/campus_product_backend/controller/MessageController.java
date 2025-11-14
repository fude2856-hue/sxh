package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.entity.*;
import com.dz.campus_product_backend.service.MessageService;
import com.dz.campus_product_backend.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/comment")
    public ResultVO getEvaluationMessages(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "3") int pageSize,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum,pageSize);
        ResultVO resultVO= messageService.getEvaluationMessages(user.getId());
        PageInfo<Evaluation> pageInfo=new PageInfo<>((List<Evaluation>)resultVO.getData());
        resultVO.setData(pageInfo);
        return resultVO;
    }
    @PostMapping("/add")
    public ResultVO sendFeedback(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String content=map.get("content").toString();
        User user = (User)request.getSession().getAttribute("user");
        return messageService.addFeedback(user.getId(),content);
    }
    @GetMapping("/order")
    public ResultVO getOrderMessages(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "3") int pageSize,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum,pageSize);
        ResultVO resultVO= messageService.getOrderMessages(user.getId());
        PageInfo<Orders>  pageInfo=new PageInfo<>((List<Orders>)resultVO.getData());
        resultVO.setData(pageInfo);
        return resultVO;
    }
    @GetMapping("/interaction")
    public ResultVO getInteractions(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "3") int pageSize,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum,pageSize);
        ResultVO resultVO= messageService.getInteractionsMessages(user.getId());
        PageInfo<Interaction>  pageInfo=new PageInfo<>((List<Interaction>)resultVO.getData());
        resultVO.setData(pageInfo);
        return resultVO;
    }
    @GetMapping("/message")
    public ResultVO getReplyMessages(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "3") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        ResultVO resultVO=messageService.getReplyMessages("1");
        PageInfo<Message> pageInfo=new PageInfo<>((List<Message>)resultVO.getData());
        resultVO.setData(pageInfo);
        return resultVO;
    }
}
