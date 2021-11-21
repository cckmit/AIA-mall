package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.oms.model.OmsCartItem;
import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @Description:
 * @Name: CartProductDTO
 * @Author peipei
 * @Date 2021/10/21
 */
@Api("购物车商品详情")
@Data
public class CartItemsDTO extends OmsCartItem {

    //购物车内某一商品 的真实库存(真实库存=实际库存-锁定库存)
    private Integer stock;
}
