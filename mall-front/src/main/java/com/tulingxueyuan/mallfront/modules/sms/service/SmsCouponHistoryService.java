package com.tulingxueyuan.mallfront.modules.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsCoupon;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsCouponHistory;

import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-10-13
 */
public interface SmsCouponHistoryService extends IService<SmsCouponHistory> {

    List<SmsCoupon> getCouponList();

}
