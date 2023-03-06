package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.entity.SysMenu;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author muzi
 * @since 2022-08-20
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 获取用户菜单列表
     */
    List<SysMenu> getUserMenuList(boolean pc, Long userId);

    List<SysMenu> queryListParentId(boolean pc, Long parentId, List<Long> menuIdList);

    List<SysMenu> queryListParentId(boolean pc, Long parentId);

}
