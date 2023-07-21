package com.example.demo.modules.quartz.service.impl;

import com.example.demo.modules.quartz.dao.ScheduleJobMapper;
import com.example.demo.modules.quartz.entity.ScheduleJob;
import com.example.demo.modules.quartz.entity.ScheduleRunnable;
import com.example.demo.modules.quartz.service.ScheduledTaskService;
import com.example.demo.modules.quartz.task.ScheduledOfTask;
import com.example.demo.utils.ExecBot;
import groovy.util.logging.Slf4j;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    private Logger log = Logger.getLogger(ScheduledTaskServiceImpl.class);
    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private ExecutorService service = Executors.newSingleThreadExecutor();

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

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

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
        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            logger.info("任务准备执行，任务ID：" + sj.getJobId());
            ScheduleRunnable task1 = new ScheduleRunnable(sj.getBeanName(),
                    sj.getMethodName(), sj.getParams());
            Future<?> future = service.submit(task1);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;

            logger.info("任务执行完毕，任务ID：" + sj.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + sj.getJobId(), e);
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            //任务状态    0：成功    1：失败
        }finally {
        }
//        Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
//        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.schedule(task,(triggerContext -> new CronTrigger(sj.getCronExpression()).nextExecutionTime(triggerContext)));
//        scheduledFutureMap.put(sj.getMethodName(),scheduledFuture);
    }

    @Override
    public void initTask() {
        Map<String,Object> map =new HashMap<>();
        List<ScheduleJob> list = scheduleJobMapper.queryList(map);
        for (ScheduleJob sj : list) {
            if("-1".equals(sj.getStatus())) //未启用
                continue;
            doStartTask(sj);
        }
    }
    public void All_Bot(){
        //梯子在自己电脑上就写127.0.0.1  软路由就写路由器的地址
        String proxyHost = "127.0.0.1";
        //端口根据实际情况填写
        int proxyPort = 1080;

        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyHost(proxyHost);
        botOptions.setProxyPort(proxyPort);
        //注意一下这里，ProxyType是个枚举，看源码你就知道有NO_PROXY,HTTP,SOCKS4,SOCKS5;
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        DefaultBotSession defaultBotSession = new DefaultBotSession();
        defaultBotSession.setOptions(botOptions);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(defaultBotSession.getClass());


            //需要代理
//            ExecBot bot = new ExecBot(botOptions);
//            telegramBotsApi.registerBot(bot);
            //不需代理
            ExecBot bot2 = new ExecBot();
            telegramBotsApi.registerBot(bot2);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}