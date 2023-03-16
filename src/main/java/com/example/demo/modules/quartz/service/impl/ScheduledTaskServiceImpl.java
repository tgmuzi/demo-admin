package com.example.demo.modules.quartz.service.impl;

import cn.hutool.core.lang.Assert;
import com.example.demo.modules.quartz.entity.ScheduleJob;
import com.example.demo.modules.quartz.service.IScheduleJobService;
import com.example.demo.modules.quartz.service.ScheduledTaskService;
import com.example.demo.modules.quartz.task.ScheduledOfTask;
import com.example.demo.modules.quartz.utils.SpringContextUtil;
import groovy.util.logging.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    private Logger log = Logger.getLogger(ScheduledTaskServiceImpl.class);

    /**
     * 可重入锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 定时任务线程池
     */
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 启动状态的定时任务集合
     */
    public Map<String, ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<>();

//    @Autowired
//    private IScheduleJobService scheduledJobService;

    @Override
    public Boolean start(ScheduleJob scheduledJob) {
        String jobKey = scheduledJob.getBeanName();
        log.info("启动定时任务"+jobKey);
        //添加锁放一个线程启动，防止多人启动多次
        lock.lock();
        log.info("加锁完成");

        try {
            if(this.isStart(jobKey)){
                log.info("当前任务在启动状态中");
                return false;
            }
            //任务启动
            this.doStartTask(scheduledJob);
        } finally {
            lock.unlock();
            log.info("解锁完毕");
        }

        return true;
    }

    /**
     * 任务是否已经启动
     */
    private Boolean isStart(String taskKey) {
        //校验是否已经启动
        if (scheduledFutureMap.containsKey(taskKey)) {
            if (!scheduledFutureMap.get(taskKey).isCancelled()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean stop(String jobKey) {
        log.info("停止任务 "+jobKey);
        boolean flag = scheduledFutureMap.containsKey(jobKey);
        log.info("当前实例是否存在 "+flag);
        if(flag){
            ScheduledFuture scheduledFuture = scheduledFutureMap.get(jobKey);

            scheduledFuture.cancel(true);

            scheduledFutureMap.remove(jobKey);
        }
        return flag;
    }

    @Override
    public Boolean restart(ScheduleJob scheduledJob) {
        log.info("重启定时任务"+scheduledJob.getBeanName());
        //停止
        this.stop(scheduledJob.getBeanName());

        return this.start(scheduledJob);
    }

    /**
     * 执行启动任务
     */
    public void doStartTask(ScheduleJob sj){
        log.info(sj.getMethodName());
        if( !"1".equals(sj.getStatus()))
            return;
        Class<?> clazz;
        ScheduledOfTask task;
        try {
            clazz = Class.forName(sj.getMethodName());
            task = (ScheduledOfTask) SpringContextUtil.getBean(clazz);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("spring_scheduled_cron表数据" + sj.getMethodName() + "有误", e);
        }
        Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.schedule(task,(triggerContext -> new CronTrigger(sj.getCronExpression()).nextExecutionTime(triggerContext)));
        scheduledFutureMap.put(sj.getMethodName(),scheduledFuture);
    }

    @Override
    public void initTask() {
//        List<ScheduleJob> list = scheduledJobService.list("handsfeeRechargeQueryTask");
//        for (ScheduleJob sj : list) {
//            if("-1".equals(sj.getStatus())) //未启用
//                continue;
//            doStartTask(sj);
//        }
    }
}