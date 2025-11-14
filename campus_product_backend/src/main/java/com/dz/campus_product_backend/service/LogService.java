package com.dz.campus_product_backend.service;

import com.dz.campus_product_backend.entity.OperationLog;
import com.dz.campus_product_backend.vo.ResultVO;

public interface LogService {
    ResultVO insertLog(OperationLog Log);


    ResultVO getAllLogs();
}
