package com.geekcattle.controller.console;

import com.geekcattle.conf.shiro.AdminShiroUtil;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.console.Menu;
import com.geekcattle.model.console.Role;
import com.geekcattle.service.console.*;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.console.MenuTreeUtil;
import org.apache.shiro.SecurityUtils;
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

@Controller
@RequestMapping(value = "/console")
public class MainController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        Admin admin = AdminShiroUtil.getUserInfo();
        List<Menu> treeGridList = this.getMenu(admin);
        model.addAttribute("admin", admin);
        model.addAttribute("menuLists", treeGridList);
        return "console/index";
    }

    @RequestMapping(value = "/wapper", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap wapper() {
        try {
            Admin admin = AdminShiroUtil.getUserInfo();
            List<Menu> treeGridList = this.getMenu(admin);
            ModelMap mp = new ModelMap();
            mp.put("admin", admin);
            mp.put("menuLists", treeGridList);
            return ReturnUtil.Success(null, mp, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.Error(null, null, null);
        }
    }

    private List<Menu> getMenu(Admin admin) {
        List<Menu> menuLists = null;
        if(admin.getIsSystem() == 1){
            menuLists = menuService.selectAllMenu();
        }else{
            menuLists = menuService.selectMenuByAdminId(admin.getUid());
        }
        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuLists,null);
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
            return ReturnUtil.Success(null, this.getTotal(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.Error(null, null, null);
        }
    }

    private Map<String, Object> getTotal() {
        Example exampleAdmin = new Example(Admin.class);
        Integer adminCount = adminService.getCount(exampleAdmin);
        Example exampleRole = new Example(Role.class);
        Integer roleCount = roleService.getCount(exampleRole);
        Example exampleMenu = new Example(Menu.class);
        Integer menuCount = menuService.getCount(exampleMenu);
        Map<String, Object> mp = new HashMap<>();
        mp.put("admin", adminCount);
        mp.put("role", roleCount);
        mp.put("menu", menuCount);
        return mp;
    }

}
