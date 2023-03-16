package com.example.demo.modules.quartz.dao;

import com.example.demo.modules.quartz.entity.ScheduleJob;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务表 Mapper 接口
 * </p>
 *
 * @author muzi
 * @since 2023-03-15
 */
@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {

    List<ScheduleJob> queryObject(String value);
    List<ScheduleJob> queryDbJobByBeanName(String value);
    List<ScheduleJob> queryList(RowBounds rowBounds, Map<String ,Object> map);
}
