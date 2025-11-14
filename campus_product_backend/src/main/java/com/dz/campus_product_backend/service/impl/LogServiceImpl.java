package com.dz.campus_product_backend.service.impl;

import com.dz.campus_product_backend.dao.LogMapper;
import com.dz.campus_product_backend.entity.OperationLog;
import com.dz.campus_product_backend.service.LogService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public ResultVO insertLog(OperationLog Log) {
        int result = logMapper.insert(Log);
        if(result>0){
            return ResultVO.success("日志插入成功");
        }
        return ResultVO.error("日志插入失败");
    }

    @Override
    public ResultVO getAllLogs() {
        List<OperationLog> logs = logMapper.selectAllLogs();
        return ResultVO.success(logs);
    }
}
