package com.example.demo.modules.quartz.task;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;

@Component("handsfeeRechargeQueryTask")
public class HandsfeeRechargeQueryTask {

    private Logger log = Logger.getLogger(HandsfeeRechargeQueryTask.class);

    public void run() throws ParseException {
        log.info("执行任务handsfeeRechargeQueryTask： "+ LocalDateTime.now());
    }
}