package com.tulingxueyuan.mallfront.modules.oms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tulingxueyuan.mallfront.dto.CartItemsDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsCartItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
public interface OmsCartItemMapper extends BaseMapper<OmsCartItem> {


    List<CartItemsDTO> getCartList(Long memberId);

    List<CartItemsDTO> getCartProductDTOByIds(@Param(Constants.WRAPPER) Wrapper ew);


}
