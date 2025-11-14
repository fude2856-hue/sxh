package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.InteractionMapper;
import com.dz.campus_product_backend.entity.Interaction;
import com.dz.campus_product_backend.service.InteractionService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {
    @Autowired
    private InteractionMapper interactionMapper;
    @Override
    public ResultVO InteractionOperation(String user_id, String product_id) {
        Interaction interaction=interactionMapper.findByUserIdAndProductId(user_id,product_id);
        if(interaction==null){
            Interaction interaction1=new Interaction();
            interaction1.setUser_id(user_id);
            interaction1.setProduct_id(product_id);
            interaction1.setDatetime(new Date());
            int result=interactionMapper.insert(interaction1);
            if(result>0){
                return ResultVO.success("收藏成功");
            }
            return ResultVO.error("收藏失败");
        }
        else{
            int result1=interactionMapper.delete(user_id,product_id);
            if(result1>0){
                return ResultVO.success("取消收藏成功");
            }
            return ResultVO.error("取消收藏失败");
        }
    }




    @Override
    public int insertInteraction(Interaction interaction) {
        //新增收藏
        return interactionMapper.insertInteraction(interaction);
    }

    @Override
    public int updateInteractionById(Interaction interaction) {
        return interactionMapper.updateInteractionById(interaction);

    }

    @Override
    public Interaction getInteractionById(String id) {
        return null;
    }

    @Override
    public List<Interaction> getInteractionsByPage(int pageNum, int pageSize, String keyword) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        int offset = (pageNum - 1) * pageSize;
        return interactionMapper.getInteractionsByPage(pageNum, pageSize, keyword, offset);
    }
    public int getTotalCount(String keyword) {
        return interactionMapper.getTotalCount(keyword);
    }

}
