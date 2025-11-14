package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

public interface FeedbackService {
    ResultVO getFeedback(String UserId);
    ResultVO addReplyToFeedback(String receive_user_id,String content);
}
