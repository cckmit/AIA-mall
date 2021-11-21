package com.tulingxueyuan.mallfront.modules.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.modules.sms.mapper.SmsCouponHistoryMapper;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsCoupon;
import com.tulingxueyuan.mallfront.modules.sms.model.SmsCouponHistory;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsCouponHistoryService;
import com.tulingxueyuan.mallfront.modules.sms.service.SmsCouponService;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-10-13
 */
@Service
public class SmsCouponHistoryServiceImpl extends ServiceImpl<SmsCouponHistoryMapper, SmsCouponHistory> implements SmsCouponHistoryService {
    @Autowired
    UmsMemberService umsMemberService;

    @Autowired
    SmsCouponService smsCouponService;

    @Override
    public List<SmsCoupon> getCouponList() {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        List<Long> couponIds = new ArrayList<>();
        LambdaQueryWrapper<SmsCouponHistory> historyLambdaQueryWrapper = new QueryWrapper<SmsCouponHistory>().lambda();
        historyLambdaQueryWrapper.eq(SmsCouponHistory::getMemberId, currentMember.getId());
        //查找出用户的优惠券id
        List<SmsCouponHistory> couponHistoryList = this.list(historyLambdaQueryWrapper);
        couponHistoryList.stream().forEach(couponList->couponIds.add(couponList.getCouponId()));
        List<SmsCoupon> smsCouponList = smsCouponService.listByIds(couponIds);
        return smsCouponList;
    }
}
