package com.geekcattle.model.valid;

import javax.validation.constraints.NotEmpty;

/**
 * @author geekcattle
 */
public class ValidAdmin {

    @NotEmpty(message="用户名不能为空")
    private String username;

    @NotEmpty(message="密码不能为空")
    private String password;

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
}
