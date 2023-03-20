package com.example.demo.modules.quartz.job;

import com.example.demo.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    @Scheduled(cron = "0/5 * * * * ?")
    public void reptilian(){
        HttpUtils httpUtils=new HttpUtils();
        List<NameValuePair> parametersBody = new ArrayList();
        parametersBody.add(new BasicNameValuePair("userId", "admin"));
        try {
            String result = HttpUtils.getRequest("http://www.test.com/user",parametersBody);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("执行调度任务："+new Date());
    }
}