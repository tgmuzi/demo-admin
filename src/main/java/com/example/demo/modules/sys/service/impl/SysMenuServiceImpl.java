package com.example.demo.modules.sys.service.impl;

import com.example.demo.modules.sys.entity.SysMenu;
import com.example.demo.modules.sys.dao.SysMenuMapper;
import com.example.demo.modules.sys.service.ISysMenuService;
import com.example.demo.utils.Constant.MenuType;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author muzi
 * @since 2022-08-20
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getUserMenuList(boolean pc, Long userId) {
        // 用户菜单列表
        List<Long> menuIdList = sysMenuMapper.queryAllMenuId(pc, 0L);
        return getAllMenuList(pc, menuIdList);
    }

    @Override
    public List<SysMenu> queryListParentId(boolean pc, Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = queryListParentId(pc, parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenu> userMenuList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenu> queryListParentId(boolean pc, Long parentId) {
        // 用户菜单列表
        return sysMenuMapper.queryListParentId(pc, parentId);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList(boolean pc, List<Long> menuIdList) {
        // 查询根菜单列表
        List<SysMenu> menuList = queryListParentId(pc, 0L, menuIdList);

        // 菜单名国际化
        /* menuList = I18nUtils.i18nSysMenu(localeMessage.getLocale(), menuList); */

        // 递归获取子菜单
        getMenuTreeList(pc, menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(boolean pc, List<SysMenu> menuList, List<Long> menuIdList) {
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();
        for (SysMenu entity : menuList) {
            if (entity.getType() == MenuType.CATALOG.getValue()) {// 目录 @E20180704 左边列表栏只要显示一级菜单，所以无需改动
                List<SysMenu> sysMenus = queryListParentId(pc, entity.getMenuId(), menuIdList);
                entity.setList(getMenuTreeList(pc, sysMenus, menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

}
