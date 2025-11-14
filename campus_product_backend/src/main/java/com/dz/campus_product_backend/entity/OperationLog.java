package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OperationLog {
    private String id;
    private String user_id;
    private String detail;
    private String create_time;
}
