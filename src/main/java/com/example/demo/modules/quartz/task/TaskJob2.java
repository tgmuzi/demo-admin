package com.example.demo.modules.quartz.task;

import groovy.util.logging.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class TaskJob2 implements ScheduledOfTask{
    private Logger log = Logger.getLogger(TaskJob2.class);
    @Override
    public void execute() {
        log.info("执行任务2 "+ LocalDateTime.now());
    }
}