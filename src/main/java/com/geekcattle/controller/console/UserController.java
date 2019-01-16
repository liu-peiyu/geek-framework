package com.geekcattle.controller.console;

import com.geekcattle.model.member.Member;
import com.geekcattle.service.member.MemberService;
import com.geekcattle.util.ReturnUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author geekcattle
 */
@Controller
@RequestMapping("/console/member")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @RequiresPermissions("member:index")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/member/index";
    }

    @RequiresPermissions("member:index")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Member member) {
        ModelMap map = new ModelMap();
        List<Member> lists = memberService.getPageList(member);
        map.put("pageInfo", new PageInfo<Member>(lists));
        map.put("queryParam", member);
        return ReturnUtil.success("加载成功", map, null);
    }

}
