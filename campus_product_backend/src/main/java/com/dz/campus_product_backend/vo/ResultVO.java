package com.dz.campus_product_backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    private int code;
    private String msg;
    private Object data;

    public static ResultVO success(Object data) {
        return new ResultVO(0, "success", data);
    }
    public static ResultVO error(String msg) {
        return new ResultVO(1, msg, null);
    }
}
