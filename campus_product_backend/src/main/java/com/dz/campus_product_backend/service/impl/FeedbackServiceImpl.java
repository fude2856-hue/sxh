package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.FeedbackMapper;
import com.dz.campus_product_backend.entity.Message;
import com.dz.campus_product_backend.service.FeedbackService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    public ResultVO getFeedback(String UserId) {
        List<Message> feedbacksenders=feedbackMapper.selectAllFeedback(UserId);
        return ResultVO.success(feedbacksenders);
    }
    @Override
    public ResultVO addReplyToFeedback(String feedbackId,String content) {
        Message originalFeedback=feedbackMapper.selectFeedbackByUserId(feedbackId);
        if (originalFeedback.getSend_user_id() == null || originalFeedback.getSend_user_id().isEmpty()) {
            System.out.println("原始反馈缺少"+feedbackId);
            return ResultVO.error("原始反馈数据不完整");
        }
        Message reply=new Message();
        reply.setReceive_user_id(originalFeedback.getSend_user_id());
        reply.setCreate_time(String.valueOf(LocalDateTime.now()));
        reply.setSend_user_id("1");
        reply.setContent(content);
        reply.setIs_read("0");
        int i=feedbackMapper.addReply(reply);
        return i>0?ResultVO.success("回复成功"):ResultVO.error("回复失败");
    }

}
