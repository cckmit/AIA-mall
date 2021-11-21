package com.tulingxueyuan.mallfront.modules.oms.mapper;

import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mallfront.dto.OrderDTO;
import com.tulingxueyuan.mallfront.dto.OrderDetailDTO;
import com.tulingxueyuan.mallfront.dto.OrderQueryDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {

    IPage<OrderQueryDTO> getUserOrders(Page<?> page, @Param("orderQueryDTO") OrderQueryDTO orderQueryDTO,
                                       @Param("memberId") Long memberId,@Param("productIds") List<Long> productIds);

    OrderDetailDTO getOrderDetail(Long orderId);

}
