package com.geekcattle.model.valid;

import org.hibernate.validator.constraints.NotEmpty;
//更多验证规则 http://www.iteye.com/topic/1144595

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
