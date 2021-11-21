package com.tulingxueyuan.mallfront.modules.oms.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order")
@ApiModel(value = "OmsOrder对象", description = "订单表")
public class OmsOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long memberId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "提交时间")
    private Date createTime;

    @ApiModelProperty(value = "用户帐号")
    private String memberUsername;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "应付金额（实际支付金额）")
    private BigDecimal payAmount;


    @ApiModelProperty(value = "支付方式：0->未支付；1->支付宝；2->微信")
    private Integer payType;


    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;

    @ApiModelProperty(value = "订单类型：0->支付订单；1->被支付订单")
    private Integer orderType;


    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "收货人邮编")
    private String receiverPostCode;

    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    private String receiverCity;

    @ApiModelProperty(value = "区")
    private String receiverRegion;

    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty(value = "订单备注")
    private String note;

    @ApiModelProperty(value = "确认收货状态：0->未确认；1->已确认")
    private Integer confirmStatus;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;


    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;

    @ApiModelProperty(value = "确认收货时间")
    private Date receiveTime;

    @ApiModelProperty(value = "评价时间")
    private Date commentTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "发货人姓名")
    private String consignorName;

    @ApiModelProperty(value = "商品skuId")
    private Long skuId;

    @ApiModelProperty("商品skuId")
    private Long productId;

    @ApiModelProperty("发货人手机号")
    private String consignorPhone;

    @ApiModelProperty("商品件数")
    private Integer productQuantity;

    @ApiModelProperty("订单评价等级")
    private Integer orderRate;


}
