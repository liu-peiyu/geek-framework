package com.geekcattle.controller.console;

import com.geekcattle.core.utils.ShiroUtil;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.console.Menu;
import com.geekcattle.model.console.Role;
import com.geekcattle.model.member.Member;
import com.geekcattle.service.console.*;
import com.geekcattle.service.member.MemberService;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.model.console.MenuTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geekcattle
 */
@Controller
@RequestMapping(value = "/console")
public class MainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuService menuService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        Admin admin = ShiroUtil.getUserInfo();
        List<Menu> treeGridList = this.getMenu(admin);
        model.addAttribute("admin", admin);
        model.addAttribute("menuLists", treeGridList);
        return "console/index";
    }

    @RequestMapping(value = "/wapper", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap wapper() {
        try {
            Admin admin = ShiroUtil.getUserInfo();
            List<Menu> treeGridList = this.getMenu(admin);
            ModelMap mp = new ModelMap();
            mp.put("admin", admin);
            mp.put("menuLists", treeGridList);
            return ReturnUtil.success(null, mp, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.error(null, null, null);
        }
    }

    private List<Menu> getMenu(Admin admin) {
        List<Menu> menuLists = null;
        if(admin.getIsSystem() == 1){
            menuLists = menuService.selectAllMenu();
        }else{
            menuLists = menuService.selectMenuByAdminId(admin.getUid());
        }
        MenuTree menuTreeUtil = new MenuTree(menuLists,null);
        return menuTreeUtil.buildTreeGrid();
    }

    @RequestMapping(value = "/main", method = {RequestMethod.GET})
    public String right(Model model) {
        model.addAllAttributes(this.getTotal());
        return "console/right";
    }

    @RequestMapping(value = "/main", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap main() {
        try {
            return ReturnUtil.success(null, this.getTotal(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.error(null, null, null);
        }
    }

    private Map<String, Object> getTotal() {
        Example exampleAdmin = new Example(Admin.class);
        Integer adminCount = adminService.getCount(exampleAdmin);
        Example exampleRole = new Example(Role.class);
        Integer roleCount = roleService.getCount(exampleRole);
        Example exampleMenu = new Example(Menu.class);
        Integer menuCount = menuService.getCount(exampleMenu);
        Example exampleMember = new Example(Member.class);
        Integer userCount = memberService.getCount(exampleMember);
        Map<String, Object> mp = new HashMap<>(4);
        mp.put("user", userCount);
        mp.put("admin", adminCount);
        mp.put("role", roleCount);
        mp.put("menu", menuCount);
        return mp;
    }

}
