package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.Interaction;
import com.dz.campus_product_backend.vo.ResultVO;

import java.util.List;

public interface InteractionService {
    ResultVO InteractionOperation(String user_id, String product_id);


    //增加
    int insertInteraction(Interaction interaction);

    //根据id修改
    int updateInteractionById(Interaction interaction);
    //根据id查询
    Interaction getInteractionById(String id);


    List<Interaction> getInteractionsByPage(int pageNum, int pageSize, String keyword);

    public int getTotalCount(String keyword);
}
