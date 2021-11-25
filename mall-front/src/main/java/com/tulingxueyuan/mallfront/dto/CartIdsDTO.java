package com.tulingxueyuan.mallfront.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Name: CartIdsDTO
 * @Author peipei
 * @Date 2021/11/22
 */
@Data
public class CartIdsDTO {
    private List<Long> ids;

    private Integer starStatus;
}
