package com.dz.campus_product_backend.dao;


import com.dz.campus_product_backend.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {
    int insert(OperationLog Log);


    List<OperationLog> selectAllLogs(); // 新增：获取所有日志（按时间降序）
}
