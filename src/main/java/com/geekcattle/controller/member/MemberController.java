/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.controller.member;

import com.geekcattle.core.LoginEnum;
import com.geekcattle.core.shiro.CustomerAuthenticationToken;
import com.geekcattle.model.member.Member;
import com.geekcattle.model.valid.ValidMember;
import com.geekcattle.service.member.MemberService;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.PasswordUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;

/**
 * author geekcattle
 * date 2017/3/9 0009 下午 14:28
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    /**
     * 处理登录操作
     *
     * @param member
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ModelMap doReg(@Valid Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ReturnUtil.Error("用户名或密码为空", null, null);
        }
        try {
            Example example = new Example(Member.class);
            example.createCriteria().andCondition("account = ", member.getAccount());
            Integer userCount = memberService.getCount(example);
            if (userCount > 0) {
                return ReturnUtil.Error("用户名已存在", null, null);
            }
            if (StringUtils.isEmpty(member.getPassword())) {
                return ReturnUtil.Error("密码不能为空", null, null);
            }
            String Id = UuidUtil.getUUID();
            member.setUid(Id);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(member.getPassword());
            member.setPassword(password);
            member.setState(1);
            member.setCreatedAt(DateUtil.getCurrentTime());
            member.setUpdatedAt(DateUtil.getCurrentTime());
            memberService.insert(member);
            return ReturnUtil.Success("操作成功", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        try {
            System.out.println("MemberController.logout()");
            SecurityUtils.getSubject().logout();
            System.out.println("您已安全退出");
        }catch (Exception e){
            System.out.println(e);
        }
        return "redirect:/member/login";

    }

}
