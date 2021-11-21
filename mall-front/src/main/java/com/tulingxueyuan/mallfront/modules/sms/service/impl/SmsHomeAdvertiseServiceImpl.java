package com.tulingxueyuan.mallfront.modules.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.dto.HomeRecommendDTO;
import com.tulingxueyuan.mallfront.modules.sms.mapper.SmsHomeAdvertiseMapper;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeAdvertise;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeCategory;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsHomeAdvertiseService;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsHomeCategoryService;
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
}
