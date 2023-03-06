package com.example.demo.modules.sys.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modules.AbstractController;
import com.example.demo.modules.sys.entity.SysMenu;
import com.example.demo.modules.sys.service.ISysMenuService;
import com.example.demo.utils.AjaxObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author muzi
 * @since 2022-08-20
 */
@Controller
@RequestMapping("${adminPath}/sys/sysMenu")
public class SysMenuController extends AbstractController {

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 用户菜单列表
     */
    @PostMapping("/user")
    @ResponseBody
    public AjaxObject user(HttpServletRequest request) {
        List<SysMenu> menuList = sysMenuService.getUserMenuList(true, getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("menuList", menuList);
        return AjaxObject.ok().data(map);
    }
}
