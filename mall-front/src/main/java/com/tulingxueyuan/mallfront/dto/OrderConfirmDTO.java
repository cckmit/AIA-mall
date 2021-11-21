package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsCompanyAddress;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsCoupon;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberReceiveAddress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @Name: ConfirmOrderDTO
 * @Author peipei
 * @Date 2021/10/25
 */
@Api("确认订单信息DTO")
@Data
public class OrderConfirmDTO {

    //订单所包含的商品列表
    private List<OmsCartItem> cartList;

    //订单所包含的商品总额
    private BigDecimal priceTotal;


    //订单总支付金额
    private BigDecimal payAmount;


    //订单总商品数量
    private Integer productTotal;

    private List<UmsMemberReceiveAddress> addressList;

    //用户持有的优惠券
    private List<SmsCoupon> couponHistoryDetailList;

}
