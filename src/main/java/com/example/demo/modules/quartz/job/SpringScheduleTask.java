package com.example.demo.modules.quartz.job;

import com.example.demo.utils.ExecBot;
import com.example.demo.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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