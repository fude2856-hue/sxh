package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Evaluation;
import com.dz.campus_product_backend.entity.Interaction;
import com.dz.campus_product_backend.entity.Message;
import com.dz.campus_product_backend.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    List<Evaluation> getEvaluationMessages(@Param("user_id") String user_id);
    List<Orders> getOrdersMessages(@Param("user_id") String user_id);
    //提交反馈
    int addMessage(Message message);
    List<Interaction> selectInteractions(@Param("user_id") String user_id);
    List<Message> selectReplyMessages(@Param("sendUserId") String sendUserId);
}