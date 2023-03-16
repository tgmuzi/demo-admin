package com.example.demo.modules.quartz.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.modules.quartz.entity.ScheduleJob;
import com.example.demo.modules.quartz.dao.ScheduleJobMapper;
import com.example.demo.modules.quartz.service.IScheduleJobService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.modules.quartz.service.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 定时任务表 服务实现类
 * </p>
 *
 * @author muzi
 * @since 2023-03-15
 */
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements IScheduleJobService {

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @Override
    public boolean updateOne(ScheduleJob scheduledJob) {
        if(updateById(scheduledJob))
            scheduledTaskService.restart(scheduledJob);
        return true;
    }

    @Override
    public List<ScheduleJob> list(String value) {
        return scheduleJobMapper.queryDbJobByBeanName(value);
    }

    @Override
    public Page<ScheduleJob> queryList(Page<ScheduleJob> params) {
        params.setRecords(scheduleJobMapper.queryList(params,params.getCondition()));
        return params;
    }
}
