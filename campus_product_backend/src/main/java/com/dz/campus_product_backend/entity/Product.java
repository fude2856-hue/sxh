package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@ToString
public class Product {
    private String id;
    private String name;
    private String detail;
    private String cover_list;
    private String old_level;
    private String category_id;
    private String user_id;
    private String inventory;
    private String price;
    private String create_time;

}
