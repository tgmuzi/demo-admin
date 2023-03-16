package com.example.demo.modules.quartz.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.modules.AbstractController;
import com.example.demo.modules.quartz.entity.ScheduleJob;
import com.example.demo.modules.quartz.service.IScheduleJobService;
import com.example.demo.utils.AjaxObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 定时任务表 前端控制器
 * </p>
 *
 * @author muzi
 * @since 2023-03-15
 */
@Controller
@RequestMapping("${adminPath}/quartz/scheduleJob")
public class ScheduleJobController extends AbstractController {

    @Autowired
    private IScheduleJobService scheduleJobService;

    @RequestMapping
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("ctx", getAdminPath() + "/");
        return "modules/job/quartzDemo";
    }

    @RequestMapping("/list")
    @ResponseBody
    public AjaxObject list(HttpServletRequest request, Page<ScheduleJob> params) {
        Page<ScheduleJob> page = scheduleJobService.queryList(params);
        return AjaxObject.ok().put("page",page);
    }
}

