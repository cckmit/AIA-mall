package com.tulingxueyuan.mallfront.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Name: NavHeaderCartDTO
 * @Author peipei
 * @Date 2021/11/21
 */
@Data
public class NavHeaderCartDTO {

    //商品id
    private Long productId;

    //商品图片
    private String productPic;

    //商品名称
    private String productName;

    //    商品单价
    private BigDecimal productPrice;

    //    商品数量
    private Integer quantity;
}

