package com.dz.campus_product_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Category {
    private String id;
    private String name;
    private String is_use;
}
