package com.geekcattle.model.console;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geekcattle.model.BaseEntity;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * @author geekcattle
 */
public class Menu extends BaseEntity{

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(generator = "UUID")
    private String menuId;

    @NotEmpty(message="菜单名称不能为空")
    private String menuName;

    @Column(columnDefinition = "enum('menu','auth','button')")
    private String menuType;

    @NotEmpty(message="菜单URL不能为空")
    private String menuUrl;

    @NotEmpty(message="菜单标识不能为空")
    private String menuCode;

    @NotEmpty(message="父类ID不能为空")
    private String parentId;

    private String parentIds;

    private Integer childNum;

    private Integer listorder;

    private String createdAt;

    private String updatedAt;

    @Transient
    private List<Menu> children;

    @Transient
    private List<Role> roleList;

    @Transient
    private List<Admin> adminList;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

    public Integer getListorder() {
        return listorder;
    }

    public void setListorder(Integer listorder) {
        this.listorder = listorder;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuType='" + menuType + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentIds='" + parentIds + '\'' +
                ", listorder=" + listorder +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", children=" + children +
                ", roleList=" + roleList +
                ", adminList=" + adminList +
                '}';
    }
}