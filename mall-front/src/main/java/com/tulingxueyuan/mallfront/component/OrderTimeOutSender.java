package com.tulingxueyuan.mallfront.component;

import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/***
 * @Author peipei
 *
 * 订单超时取消并解锁库存的定时器
 */
@Component
@Slf4j
public class OrderTimeOutSender {

    @Autowired
    private OmsOrderService orderService;

    // 秒 分 小时 日期 月份 星期  年（可选）
    //    / 代表隔多少时间允许
    //     * 任何时间
    //    ?  代表无任何指定  日期、星期才能指定
    // 每个星期一执行一次
    //0 0 * * * ? *
    @Scheduled(cron = "0 0 0 0 0 1 *")
    private void cancelOverTimeOrder() {
        log.info("--------OrderTimeOutSender订单超时取消并解锁库存的定时器开始----------{}",new Date().toString());
        orderService.cancelOverTimeOrder();
        log.info("--------OrderTimeOutSender订单超时取消并解锁库存的定时器结束----------{}",new Date().toString());
    }
}
