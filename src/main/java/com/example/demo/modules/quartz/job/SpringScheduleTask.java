package com.example.demo.modules.quartz.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * spring自带任务框架，有弊端
 */
@Component
@EnableScheduling //必须加噢
public class SpringScheduleTask {

    /**
     *  每分钟执行一次
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    public void reptilian(){
        System.out.println("执行调度任务："+new Date());
    }
}