package com.geekcattle.controller.api;

import com.geekcattle.core.utils.SecurityUtil;
import com.geekcattle.util.ReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author geekcattle
 */
@RestController
@RequestMapping("/api/member")
public class ApiMemberController {

    protected  Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelMap index(){
        if(SecurityUtil.isLogin()){
            User user = SecurityUtil.getFontUserInfo();
            return ReturnUtil.success("获取用户信息成功", user);
        }else{
            return ReturnUtil.error("用户不存在");
        }

    }

}
