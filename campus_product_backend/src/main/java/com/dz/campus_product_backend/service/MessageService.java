package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Message;
import com.dz.campus_product_backend.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

public interface MessageService {
    ResultVO getEvaluationMessages(@Param("user_id") String user_id);
    ResultVO addFeedback(String sendUserid,String content);
    ResultVO getOrderMessages(@Param("user_id") String user_id);
    ResultVO getInteractionsMessages(@Param("user_id") String user_id);
    ResultVO getReplyMessages(String sendUserId);
}

