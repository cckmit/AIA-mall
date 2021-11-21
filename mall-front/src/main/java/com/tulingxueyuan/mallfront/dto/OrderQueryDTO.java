package com.tulingxueyuan.mallfront.dto;

import io.swagger.annotations.Api;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @Description:
 * @Name: OrderQueryDTO
 * @Author peipei
 * @Date 2021/11/14
 */
@Api("订单分类查询DTO")
@Data
public class OrderQueryDTO {

    //订单id
    private Long orderId;

    //订单编号
    private String orderSn;

    //商品id
    private Long productId;

    //    订单状态,默认为未支付状态查询
    private Integer orderStatus;

    //    订单付款人姓名
    private String payerName;

    //发货人姓名
    private String consignorName;

    //    下单日期
    private Date createdTime;

    //订单类型
    private Integer orderType;

    //    订单总额
    private BigDecimal payAmount;

    //    订单备注
    private String node;

    //商品名称
    private String  productName;

    //商品skuId
    private Long skuId;

    //订单按金额排序,默认为金额从低到高查询
    private Integer amountSort=0;

    private Integer pageNum;

    private Integer pageSize;


}
