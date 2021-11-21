package com.tulingxueyuan.mallfront.modules.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tulingxueyuan.mallfront.dto.*;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrderItem;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
public interface OmsOrderService extends IService<OmsOrder> {

    IPage<OrderQueryDTO> getUserOrders(OrderQueryDTO orderQueryDTO);

    OrderDetailDTO getOrderDetail(Long orderId);

    OmsOrder generateOrder(GenerateOrderDTO generateOrderDTO);

    /**
     * 按订单 ID 获取订单项目
     * @param orderSn
     * @return
     */
    OmsOrder getOrderByOrderSn(String orderSn);


    /**
     * 定时任务，每个星期一自动删除过期的订单
     */
    void cancelOverTimeOrder();

    /**
     * 支付成功后的回调接口
     * @param paySuccessOrderSn
     * @param payType
     */
    void paySuccess(String paySuccessOrderSn,Integer payType);


    OrderProductDetailDTO getOrderProductDetail(Long orderId);

    boolean createOrderComment(Long orderId, String orderComment);

    boolean updateOrderStatus(Long orderId,Integer orderStatus);
}
