package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Orders {
    private String id;
    private String product_id;
    private String buy_price;
    private String trade_time;
    private String user_id;

}
