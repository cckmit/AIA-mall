package com.tulingxueyuan.mallfront.modules.oms.service;

import com.tulingxueyuan.mall.common.api.CommonResult;

/***
 * @Author peipei
 */
public interface TradeService {
    /**
     * 生成当面付二维码
     * @param orderId
     * @return
     */
    CommonResult alipayTrade(Long orderId);


    /**
     * 微信支付
     * @param id
     * @return
     */
    CommonResult wechatPayTrade(Long id);
}
