package com.tulingxueyuan.mallfront.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.dto.CartIdsDTO;
import com.tulingxueyuan.mallfront.dto.CartItemsDTO;
import com.tulingxueyuan.mallfront.modules.oms.mapper.OmsCartItemMapper;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsCartItemService;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsSkuStock;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsSkuStockService;
import com.tulingxueyuan.mallfront.modules.ums.mapper.UmsMemberMapper;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberStar;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author 720Studio
 * @since 2021-11-21
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {
    @Autowired
    UmsMemberService umsMemberService;

    @Autowired
    OmsCartItemService omsCartItemService;

    @Autowired
    UmsMemberStarService umsMemberStarService;

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Autowired
    OmsCartItemMapper omsCartItemMapper;

    @Autowired
    PmsSkuStockService pmsSkuStockService;

    @Override
    public List<CartItemsDTO> getCartList() {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        return omsCartItemMapper.getCartList(currentMember.getId());
    }

    /**
     * 更新购物车内的某一商品sku数量,以及商品sku的锁定库存和真实库存
     *
     * @param cartItemId
     * @param productSkuId
     * @param quantity
     * @return
     */
    @Override
    public boolean updateProductQuantity(Long cartItemId, Long productSkuId, Integer quantity) {
        //先更新购物车内的相关信息
        LambdaUpdateWrapper<OmsCartItem> cartItemLambdaUpdateWrapper = new UpdateWrapper<OmsCartItem>().lambda();
        cartItemLambdaUpdateWrapper.set(OmsCartItem::getQuantity, quantity)
                .eq(OmsCartItem::getId, cartItemId);
        boolean updateCartItem = this.update(cartItemLambdaUpdateWrapper);

        //  更新sku的锁定库存和真实库存
        PmsSkuStock skuById = pmsSkuStockService.getById(productSkuId);
        LambdaUpdateWrapper<PmsSkuStock> skuStockLambdaUpdateWrapper = new UpdateWrapper<PmsSkuStock>().lambda();
        skuStockLambdaUpdateWrapper.eq(PmsSkuStock::getId, productSkuId);
        if (quantity > skuById.getStock()) {
            skuStockLambdaUpdateWrapper.setSql("lock_stock=lock_stock+" + (quantity - skuById.getLockStock()));
        } else {
            skuStockLambdaUpdateWrapper.setSql("lock_stock=lock_stock-" + (skuById.getLockStock() - quantity));
        }
        boolean updateSku = pmsSkuStockService.update(skuStockLambdaUpdateWrapper);
        return updateCartItem && updateSku;
    }

    @Override
    public boolean deleteCartItem(List<Long> cartItemIds) {
        boolean updateSku = true;
        for (Long cartItemId : cartItemIds) {
            OmsCartItem cartItem = this.getById(cartItemId);
            LambdaUpdateWrapper<PmsSkuStock> skuStockLambdaUpdateWrapper = new UpdateWrapper<PmsSkuStock>().lambda();
            skuStockLambdaUpdateWrapper.setSql("lock_stock=lock_stock-" + cartItem.getQuantity())
                    .eq(PmsSkuStock::getId, cartItem.getProductSkuId());
            updateSku = pmsSkuStockService.update(skuStockLambdaUpdateWrapper);
        }
        boolean removeCartItems = this.removeByIds(cartItemIds);
        //更新sku库存
        return removeCartItems && updateSku;
    }

    @Override
    public boolean starCartProduct(CartIdsDTO cartIdsDTO) {
        List<Long> ids = cartIdsDTO.getIds();
        boolean starCartProduct = true;
        boolean starMemberProduct = true;
        LambdaUpdateWrapper<OmsCartItem> cartProductStatusWrapper = new UpdateWrapper<OmsCartItem>().lambda();
        LambdaUpdateWrapper<UmsMemberStar> memberProductStatusWrapper = new UpdateWrapper<UmsMemberStar>().lambda();
        if (cartIdsDTO.getStarStatus() == 1) {
            LambdaQueryWrapper<UmsMemberStar> starLambdaQueryWrapper = new QueryWrapper<UmsMemberStar>().lambda();
            starLambdaQueryWrapper.eq(UmsMemberStar::getMemberId, umsMemberService.getCurrentMember().getId());
            UmsMemberStar umsMemberStar = umsMemberStarService.getOne(starLambdaQueryWrapper);
            String starProductId = umsMemberStar.getStarProductId();
            String[] skuIds = starProductId.split(",");
            for (int i = 0; i < skuIds.length; i++) {
                if (!String.valueOf(skuIds[i]).equals("")&&ids.contains(Long.valueOf(String.valueOf(skuIds[i])))) {
                    String target = "," +skuIds[i];
                    starProductId = starProductId.replace(target, "");
                }
            }
            memberProductStatusWrapper.eq(UmsMemberStar::getMemberId, umsMemberService.getCurrentMember().getId())
                    .set(UmsMemberStar::getStarProductId, starProductId);
            umsMemberStarService.update(memberProductStatusWrapper);
        }
        for (Long id : ids) {
            //先更新个人star表
            if (cartIdsDTO.getStarStatus() == 0) {
                starMemberProduct = umsMemberMapper.starCartProduct(id, umsMemberService.getCurrentMember().getId());
            }
            //    接着更新购物车商品star状态
            cartProductStatusWrapper.eq(OmsCartItem::getMemberId, umsMemberService.getCurrentMember().getId())
                    .eq(OmsCartItem::getProductSkuId, id).set(OmsCartItem::getStarStatus, cartIdsDTO.getStarStatus() == 0 ? 1 : 0);
            starCartProduct = omsCartItemService.update(cartProductStatusWrapper);
        }
        return starCartProduct && starMemberProduct;
    }
}
