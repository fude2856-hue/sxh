package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.MessageMapper;
import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.entity.Interaction;
import com.dz.campus_product_backend.entity.Message;
import com.dz.campus_product_backend.entity.Orders;
import com.dz.campus_product_backend.service.MessageService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public ResultVO getEvaluationMessages(String userId) {
        List<Evaluation> evaluationMessages = messageMapper.getEvaluationMessages(userId);
        return ResultVO.success(evaluationMessages);
    }

    @Override public ResultVO addFeedback(String sendUserid,String content) {
        Message message=new Message();
        message.setSend_user_id(sendUserid);
        message.setContent(content);
        message.setReceive_user_id("1");
        message.setCreate_time(String.valueOf(LocalDateTime.now()));
        message.setIs_read("0");
        int i=messageMapper.addMessage(message);
        return i>0?ResultVO.success("反馈提交成功"):ResultVO.error("反馈提交失败");
    }
    @Override
    public ResultVO getOrderMessages(String userId) {
        List<Orders> ordersMessages = messageMapper.getOrdersMessages(userId);
        return ResultVO.success(ordersMessages);
    }

    @Override
    public ResultVO getInteractionsMessages(String user_id) {
        List<Interaction> interactionList=messageMapper.selectInteractions(user_id);
        return ResultVO.success(interactionList);
    }

    @Override
    public ResultVO getReplyMessages(String sendUserId) {
        List<Message> replyMessages=messageMapper.selectReplyMessages(sendUserId);
        return ResultVO.success(replyMessages);
    }

}
