package com.example.demo.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.demo.modules.user.entity.SysUser;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DruidConfig {

    private Logger logger = LoggerFactory.getLogger(DruidConfig.class);
    @Value("${adminPath}")
    private String adminPath;
    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

    /**
     * @return String return the adminPath
     */
    public String getAdminPath() {
        return adminPath;
    }

    protected String getUserCode() {
        return getUser().getUserName();
    }

    /**
     * @param adminPath the adminPath to set
     */
    public void setAdminPath(String adminPath) {
        this.adminPath = adminPath;
    }


}