package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.sms.model.SmsCoupon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Name: CouponHistoryDetailListDTO
 * @Author peipei
 * @Date 2021/10/28
 */
@Api("用户优惠券DTO")
@Data
public class CouponHistoryDetailListDTO {
    private SmsCoupon coupon;

    // private

}
