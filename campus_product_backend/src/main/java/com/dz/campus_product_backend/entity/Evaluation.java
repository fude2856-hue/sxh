package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Evaluation {
    private String id;
    private String user_id;
    private String product_id;
    private String score;
    private String content;
    private String create_time;
}
