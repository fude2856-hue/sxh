package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class User {
    private String id;
    private String username;
    private String password;
    private String phone;
    private String create_time;
    private String role;
    private String account_status;
}
