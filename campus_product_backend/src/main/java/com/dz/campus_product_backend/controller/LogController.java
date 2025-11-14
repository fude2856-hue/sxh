package com.dz.campus_product_backend.controller;

import com.dz.campus_product_backend.service.LogService;
import com.dz.campus_product_backend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/all")
    public ResultVO getAllLogs() {
        return logService.getAllLogs();
    }
}


