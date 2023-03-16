package com.example.demo.modules.quartz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.modules.quartz.entity.ScheduleJob;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 定时任务表 服务类
 * </p>
 *
 * @author muzi
 * @since 2023-03-15
 */
public interface IScheduleJobService extends IService<ScheduleJob>{
    /**
     * 修改定时任务，并重新启动
     * @param scheduledJob
     * @return
     */
    boolean updateOne(ScheduleJob scheduledJob);
    List<ScheduleJob> list(String value);
    Page<ScheduleJob> queryList(Page<ScheduleJob> params);
}
