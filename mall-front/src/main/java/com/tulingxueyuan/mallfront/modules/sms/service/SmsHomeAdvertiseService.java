package com.tulingxueyuan.mallfront.modules.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mallfront.dto.HomeRecommendDTO;
import com.tulingxueyuan.mallfront.dto.NavHeaderCartDTO;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeAdvertise;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-10-13
 */
public interface SmsHomeAdvertiseService extends IService<SmsHomeAdvertise> {

    List<SmsHomeAdvertise> getAdverties();

    List<HomeRecommendDTO> getGoodSale();


    List<NavHeaderCartDTO> getCartProductList();

    boolean removeCartProduct(Long productId);


}
