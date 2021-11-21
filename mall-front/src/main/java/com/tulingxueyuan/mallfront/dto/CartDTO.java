package com.tulingxueyuan.mallfront.dto;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @Description:
 * @Name: ShopCarVo
 * @Author peipei
 * @Date 2021/10/17
 */
@Api(tags = "购物车DTO")
@Data
public class CartDTO {
    private Long ProductId;

    private Long productSkuId;

    private Integer quantity;

}
