package com.example.demo.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author muzi
 * @since 2022-08-20
 */
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId("MENU_ID")
    private Long menuId;
    /**
     * 上级菜单
     */
    @TableField("PARENT_ID")
    private String parentId;
    /**
     * 菜单名称
     */
    @TableField("NAME")
    private String name;
    /**
     * 语言
     */
    @TableField("LANGUAGE")
    private String language;
    /**
     * 菜单URL
     */
    @TableField("URL")
    private String url;
    /**
     * 授权标识
     */
    @TableField("PERMS")
    private String perms;
    /**
     * 1目录 2菜单 3按钮
     */
    @TableField("TYPE")
    private Long type;
    /**
     * 图标
     */
    @TableField("ICON")
    private String icon;
    /**
     * 排序
     */
    @TableField("ORDER_NUM")
    private String orderNum;
    /**
     * 是否是手机端菜单，0不是，1是
     */
    @TableField("ISMOBILE")
    private String ismobile;
    /**
     * 菜单状态：0启用，1禁用
     */
    @TableField("STATUS")
    private String status;
    /**
     * 菜单唯一标识
     */
    @TableField("CODE")
    private String code;
    /**
     * 是否是敏感菜单
     */
    @TableField("SENSITIVE")
    private String sensitive;

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;
    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    /**
     * 
     */
    @TableField(exist = false)
    private List<?> list;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getIsmobile() {
        return ismobile;
    }

    public void setIsmobile(String ismobile) {
        this.ismobile = ismobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                ", menuId=" + menuId +
                ", parentId=" + parentId +
                ", name=" + name +
                ", language=" + language +
                ", url=" + url +
                ", perms=" + perms +
                ", type=" + type +
                ", icon=" + icon +
                ", orderNum=" + orderNum +
                ", ismobile=" + ismobile +
                ", status=" + status +
                ", code=" + code +
                ", sensitive=" + sensitive +
                "}";
    }

    /**
     * @return String return the parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName the parentName to set
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * @return Boolean return the open
     */
    public Boolean isOpen() {
        return open;
    }

    /**
     * @param open the open to set
     */
    public void setOpen(Boolean open) {
        this.open = open;
    }

    /**
     * @return List<?> return the list
     */
    public List<?> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<?> list) {
        this.list = list;
    }

}
