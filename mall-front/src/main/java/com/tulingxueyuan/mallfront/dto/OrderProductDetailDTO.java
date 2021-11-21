package com.tulingxueyuan.mallfront.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Name: OrderProductDetailDTO
 * @Author peipei
 * @Date 2021/11/16
 */

@Data
public class OrderProductDetailDTO {
    //商品名称
    private String productName;

    // 商品图片url
    private String productPic;

    //    商品品牌
    private String productBrand;

    //    该订单内的商品件数
    private Integer productQuantity;

    //    商品属性
    private String productAttribute;

    //商品分类
    private String productAttributeCategory;

    //    商品sku原价
    private BigDecimal productSkuPrice;
}
