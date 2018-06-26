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

package com.geekcattle.controller.member;

import com.geekcattle.core.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * author geekcattle
 * date 2017/3/14 0014 上午 9:54
 */
@Controller
@RequestMapping("/member")
public class HomeController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping
    public String empty(){
        return "redirect:/member/index";
    }

    @RequestMapping("/index")
    public String index(Model model){
        String account = "";
        if(SecurityUtil.isLogin()){
            User user = SecurityUtil.getFontUserInfo();
            account = user.getUsername();
        }
        model.addAttribute("account", account);
        return "member/home";
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(SecurityUtil.isLogin()){
            return "redirect:/member/index";
        }
        return "member/login";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String reg(){
        SecurityContext securityContext = SecurityUtil.getContext();
        if(SecurityUtil.isLogin()){
            return "redirect:/member/index";
        }
        return "member/reg";
    }

}
