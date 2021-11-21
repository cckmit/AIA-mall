package com.tulingxueyuan.mallfront.dto;

import io.swagger.annotations.Api;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Name: GenerateOrderDTO
 * @Author peipei
 * @Date 2021/10/29
 */

/*
    couponId: this.curCouponId,
    itemIds: this.itemIds,
    memberReceiveAddressId: item.id,
    //微信支付无优惠券
    payType: 2,
*/
@Api("从购物车生成订单DTO")
@Data
public class GenerateOrderDTO {
    //用户选择的优惠券id
    private Long couponId;

    //用户选择的收货地址
    private Long memberReceiveAddressId;

    // 购物车items的id
    private List<Long> itemIds;

}
