package com.dz.campus_product_backend.dao;

import com.dz.campus_product_backend.entity.Interaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InteractionMapper {
    int insert(Interaction interaction);
    int delete(String user_id,String product_id);
    //查询是否存在
    Interaction findByUserIdAndProductId(String user_id,String product_id);



    //增加
    int insertInteraction(Interaction interaction) ;



    //根据id修改
    int updateInteractionById(Interaction interaction);
    //根据id查询
    Interaction getInteractionById(String id);

    List<Interaction> getInteractionsByPage(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("keyword") String keyword,
            @Param("offset") int offset
    );

    int getTotalCount(String keyword);
}
