package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Message {
    private String id;
    private String receive_user_id;
    private String content;
    private String is_read;
    private String create_time;
    private String send_user_id;
}
