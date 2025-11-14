package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Interaction {
    private String id;
    private String user_id;
    private String product_id;
    private Date datetime;
}
