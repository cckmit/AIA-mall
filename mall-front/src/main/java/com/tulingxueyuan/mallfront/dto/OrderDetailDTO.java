package com.tulingxueyuan.mallfront.dto;

import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrderItem;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberReceiveAddress;
import io.swagger.annotations.Api;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @Description:
 * @Name: OrderDetailDTO
 * @Author peipei
 * @Date 2021/10/24
 */
@Api("订单详情")
@Data

public class OrderDetailDTO {
    //订单id
    private Long orderId;

    //订单编号
    private String orderSn;

    //订单状态
    private Integer orderStatus;

    //收货人昵称
    private String receiverName;

    //发货人昵称
    private String consignorName;

    private Double payAmount;

    private String receiverProvince;

    private String receiverCity;

    private String receiverRegion;

    private String receiverDetailAddress;

    //订单超时时间
    private Integer normalOrderOvertime;

    //收货人手机号
    private String receiverPhone;

    //发货人手机号
    private String consignorPhone;

    //订单创建时间
    private Date createTime;

    //订单总金额（未打折）
    private BigDecimal totalAmount;

    //订单备注
    private String note;

    //    订单的付款时间
    private Date paymentTime;

    //订单的商品件数
    private Integer productQuantity;

    //    订单评分
    private Integer orderRate;


}
