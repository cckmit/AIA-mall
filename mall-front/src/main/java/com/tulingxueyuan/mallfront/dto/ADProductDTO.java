package com.tulingxueyuan.mallfront.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Name: ADProductDTO
 * @Author peipei
 * @Date 2021/11/20
 */
@Data
public class ADProductDTO {

    //商品id
    private Long ADProductId;

    //商品图片
    private String ADProductPic;

    //    商品名称
    private String ADProductName;

    //    商品价格
    private BigDecimal ADProductPrice;


}
