package com.geekcattle.model.console;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekcattle.model.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * @author geekcattle
 */
public class Role extends BaseEntity {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(generator = "UUID")
    private String roleId;

    @NotEmpty(message="角色名称不能为空")
    private String roleName;

    private String roleDesc;

    @Column(columnDefinition="enum(1,0)")
    private Integer enable;

    private String createdAt;

    private String updatedAt;

    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";

    /**
     * 角色 -- 权限关系：多对多关系;
     */
    @Transient
    private List<Menu> menuList;

    /**
     * 用户 - 角色关系定义
     */
    ;
    @Transient
    private List<Admin> adminList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
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

    public String getSort() {
        if(StringUtils.isEmpty(sort)){
            return "createdAt";
        }else{
            return sort;
        }
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        if(StringUtils.isEmpty(sort)){
            return "desc";
        }else{
            return order;
        }
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", enable=" + enable +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", menuList=" + menuList +
                ", adminList=" + adminList +
                '}';
    }
}
