package com.dz.campus_product_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dz.campus_product_backend.dao")
public class CampusProductBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusProductBackendApplication.class, args);
    }

}
