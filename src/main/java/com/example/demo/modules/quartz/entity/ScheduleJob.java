package com.example.demo.modules.quartz.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 定时任务表
 * </p>
 *
 * @author muzi
 * @since 2023-03-15
 */
@TableName("schedule_job")
public class ScheduleJob extends Model<ScheduleJob> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("JOB_ID")
    private Long jobId;
    /**
     * 容器名
     */
    @TableField("BEAN_NAME")
    private String beanName;
    /**
     * 方法名
     */
    @TableField("METHOD_NAME")
    private String methodName;
    /**
     * 参数
     */
    @TableField("PARAMS")
    private String params;
    /**
     * 定时
     */
    @TableField("CRON_EXPRESSION")
    private String cronExpression;
    /**
     * 0启用 
     */
    @TableField("STATUS")
    private String status;
    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.jobId;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
        ", jobId=" + jobId +
        ", beanName=" + beanName +
        ", methodName=" + methodName +
        ", params=" + params +
        ", cronExpression=" + cronExpression +
        ", status=" + status +
        ", remark=" + remark +
        ", createTime=" + createTime +
        "}";
    }
}
