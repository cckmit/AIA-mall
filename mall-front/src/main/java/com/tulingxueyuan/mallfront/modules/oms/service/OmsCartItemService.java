package com.tulingxueyuan.mallfront.modules.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mallfront.dto.CartIdsDTO;
import com.tulingxueyuan.mallfront.dto.CartItemsDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsCartItem;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author 720Studio
 * @since 2021-11-21
 */
public interface OmsCartItemService extends IService<OmsCartItem> {

    List<CartItemsDTO> getCartList();

    boolean updateProductQuantity(Long cartItemId, Long productSkuId, Integer quantity);

    boolean deleteCartItem(List<Long> cartItemIds);

    boolean starCartProduct(CartIdsDTO cartIdsDTO);
}
