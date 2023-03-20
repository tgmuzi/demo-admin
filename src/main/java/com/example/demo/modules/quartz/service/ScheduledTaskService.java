package com.example.demo.modules.quartz.service;

import com.example.demo.modules.quartz.entity.ScheduleJob;

public interface ScheduledTaskService{

    Boolean start(ScheduleJob scheduledJob);

    Boolean stop(String jobKey);

    Boolean restart(ScheduleJob scheduledJob);

    void initTask();

    void All_Bot();
}