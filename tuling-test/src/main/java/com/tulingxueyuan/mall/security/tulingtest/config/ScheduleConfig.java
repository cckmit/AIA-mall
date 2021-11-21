package com.tulingxueyuan.mall.security.tulingtest.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/***
 * @Author peipei
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {

    /**
     *
     */
    @Scheduled(cron = "0/2 * * ? * ?")
    private void cancelTimeOutOrder(){
        System.out.println("定时任务");
    }

}
