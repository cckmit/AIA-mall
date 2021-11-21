package com.tulingxueyuan.mallfront.modules.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mallfront.dto.HomeRecommendDTO;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsHomeAdvertise;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-10-13
 */
public interface SmsHomeAdvertiseMapper extends BaseMapper<SmsHomeAdvertise> {

    List<HomeRecommendDTO> getGoodSale();
}
