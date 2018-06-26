/*
 * Copyright (c) 2017-2018.  放牛极客<l_iupeiyu@qq.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * </p>
 *
 */

package com.geekcattle.model.console;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekcattle.model.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotEmpty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class Admin extends BaseEntity implements Serializable {

    @Id
    @Column(name = "uid")
    @GeneratedValue(generator = "UUID")
    private String uid;

    @NotEmpty(message="账号不能为空")
    private String username;

    private String password;

    private String salt;

    private Integer state;

    private Integer isSystem;

    private String createdAt;

    private String updatedAt;

    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";

    @Transient
    private String[] roleId;

    @Transient
    private List<Role> roleList;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public String  getCreatedAt() {
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

    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }

    public String[] getRoleId() {
        return roleId;
    }

    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", state=" + state +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", roleList=" + roleList +
                '}';
    }

}