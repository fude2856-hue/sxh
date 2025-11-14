package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeedbackMapper {
    List<Message> selectAllFeedback(@Param("receive_user_id") String receive_user_id);
    int addReply(Message message);
    Message selectFeedbackByUserId(@Param("feedbackId") String feedbackId);
}
