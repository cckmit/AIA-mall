package com.tulingxueyuan.mallfront.modules.oms.mapper;

import com.tulingxueyuan.mallfront.dto.OrderConfirmDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单中所包含的商品 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
public interface OmsOrderItemMapper extends BaseMapper<OmsOrderItem> {

    OmsOrderItem getOrderItems(Long id);

    OrderConfirmDTO getConfirmProducts();

}
