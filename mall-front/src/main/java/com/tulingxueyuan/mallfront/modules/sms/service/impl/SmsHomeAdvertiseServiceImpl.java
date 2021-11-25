package com.tulingxueyuan.mallfront.modules.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.dto.HomeRecommendDTO;
import com.tulingxueyuan.mallfront.dto.NavHeaderCartDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsCartItemService;
import com.tulingxueyuan.mallfront.modules.sms.mapper.SmsHomeAdvertiseMapper;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeAdvertise;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeCategory;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsHomeAdvertiseService;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsHomeCategoryService;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 首页轮播广告表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-10-13
 */
@Service
public class SmsHomeAdvertiseServiceImpl extends ServiceImpl<SmsHomeAdvertiseMapper, SmsHomeAdvertise> implements SmsHomeAdvertiseService {
    @Autowired
    SmsHomeAdvertiseMapper homeAdvertiseMapper;

    @Autowired
    SmsHomeCategoryService homeCategoryService;

    @Autowired
    OmsCartItemService omsCartItemService;

    @Autowired
    UmsMemberService umsMemberService;

    @Override
    public List<SmsHomeAdvertise> getAdverties() {
        LambdaQueryWrapper<SmsHomeAdvertise> homeAdvertiseQueryWrapper = new QueryWrapper<SmsHomeAdvertise>().lambda();
        homeAdvertiseQueryWrapper.eq(SmsHomeAdvertise::getStatus, 1)
                .orderByDesc(SmsHomeAdvertise::getSort);
        List<SmsHomeAdvertise> homeAdvertises = homeAdvertiseMapper.selectList(homeAdvertiseQueryWrapper);
        return homeAdvertises;

    }

    @Override
    public List<HomeRecommendDTO> getGoodSale() {
        List<HomeRecommendDTO> goodSale = homeAdvertiseMapper.getGoodSale();
        return goodSale;
    }

    @Override
    public List<NavHeaderCartDTO> getCartProductList() {
        ArrayList<NavHeaderCartDTO> navHeaderCartDTOList = new ArrayList<>();
        UmsMember umsMember = umsMemberService.getCurrentMember();
        LambdaQueryWrapper<OmsCartItem> cartItemLambdaQueryWrapper = new QueryWrapper<OmsCartItem>().lambda();
        cartItemLambdaQueryWrapper.eq(OmsCartItem::getMemberId,umsMember.getId());
        List<OmsCartItem> omsCartItemList = omsCartItemService.list(cartItemLambdaQueryWrapper);
        for (OmsCartItem omsCartItem : omsCartItemList) {
            NavHeaderCartDTO navHeaderCartDTO = new NavHeaderCartDTO();
            navHeaderCartDTO.setProductId(omsCartItem.getProductId());
            navHeaderCartDTO.setProductPic(omsCartItem.getProductPic());
            navHeaderCartDTO.setProductName(omsCartItem.getProductName());
            navHeaderCartDTO.setProductPrice(omsCartItem.getPrice());
            navHeaderCartDTO.setQuantity(omsCartItem.getQuantity());
            navHeaderCartDTOList.add(navHeaderCartDTO);
        }
        return navHeaderCartDTOList;
    }

    @Override
    public boolean removeCartProduct(Long productId) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        LambdaQueryWrapper<OmsCartItem> cartItemLambdaQueryWrapper = new QueryWrapper<OmsCartItem>().lambda();
        cartItemLambdaQueryWrapper.eq(OmsCartItem::getMemberId, currentMember.getId())
                .eq(OmsCartItem::getProductId, productId);
        return omsCartItemService.remove(cartItemLambdaQueryWrapper);
    }
}
