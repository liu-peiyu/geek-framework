package com.geekcattle.controller.console;

import com.geekcattle.core.shiro.AdminShiroRealm;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.console.AdminRole;
import com.geekcattle.model.console.Role;
import com.geekcattle.service.console.AdminRoleService;
import com.geekcattle.service.console.AdminService;
import com.geekcattle.service.console.RoleService;
import com.geekcattle.util.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author geekcattle
 */
@Controller
@RequestMapping("/console/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminShiroRealm adminShiroRealm;

    @RequiresPermissions("admin:index")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/admin/index";
    }

    @RequiresPermissions("admin:edit")
    @RequestMapping(value = "/from", method = {RequestMethod.GET})
    public String from(Admin admin, Model model) {
        String checkRoleId = "";
        if (!StringUtils.isEmpty(admin.getUid())) {
            admin = adminService.getById(admin.getUid());
            if (!"null".equals(admin)) {
                AdminRole adminRole = new AdminRole();
                adminRole.setAdminId(admin.getUid());
                List<AdminRole> adminRoleLists = adminRoleService.getRoleList(adminRole);
                admin.setUpdatedAt(DateUtil.getCurrentTime());
                ArrayList<String> checkRoleIds = new ArrayList<String>();
                for (AdminRole adminRoleList : adminRoleLists) {
                    checkRoleIds.add(adminRoleList.getRoleId());
                }
                checkRoleId = String.join(",", checkRoleIds);
            }
        } else {
            admin.setIsSystem(0);
        }
        model.addAttribute("checkRoleId", checkRoleId);
        model.addAttribute("roleLists", this.getRoleList());
        model.addAttribute("admin", admin);
        return "console/admin/from";
    }

    private List<Role> getRoleList() {
        ModelMap map = new ModelMap();
        List<Role> roleList = roleService.getFromAll();
        return roleList;
    }

    @RequiresPermissions("admin:index")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Admin admin) {
        ModelMap map = new ModelMap();
        List<Admin> lists = adminService.getPageList(admin);
        for (Admin list : lists) {
            List<Role> rolelist = roleService.selectRoleListByAdminId(list.getUid());
            list.setRoleList(rolelist);
        }
        map.put("pageInfo", new PageInfo<Admin>(lists));
        map.put("queryParam", admin);
        return ReturnUtil.success("加载成功", map, null);
    }


    @Transactional
    @RequiresPermissions("admin:save")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap save(@Valid Admin admin, BindingResult result) {
        try {
            if (result.hasErrors()) {
                for (ObjectError er : result.getAllErrors()) {
                    return ReturnUtil.error(er.getDefaultMessage(), null, null);
                }
            }
            if (StringUtils.isEmpty(admin.getUid())) {
                Example example = new Example(Admin.class);
                example.createCriteria().andCondition("username = ", admin.getUsername());
                Integer userCount = adminService.getCount(example);
                if (userCount > 0) {
                    return ReturnUtil.error("用户名已存在", null, null);
                }
                if (StringUtils.isEmpty(admin.getPassword())) {
                    return ReturnUtil.error("密码不能为空", null, null);
                }
                String id = UuidUtil.getUUID();
                admin.setUid(id);
                String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
                admin.setSalt(salt);
                String password = PasswordUtil.createAdminPwd(admin.getPassword(), admin.getCredentialsSalt());
                admin.setPassword(password);
                admin.setIsSystem(0);
                admin.setCreatedAt(DateUtil.getCurrentTime());
                admin.setUpdatedAt(DateUtil.getCurrentTime());
                adminService.insert(admin);
            } else {
                Admin updateAdmin = adminService.getById(admin.getUid());
                if (!"null".equals(updateAdmin)) {
                    admin.setSalt(updateAdmin.getSalt());
                    if (!StringUtils.isEmpty(admin.getPassword())) {
                        String password = PasswordUtil.createAdminPwd(admin.getPassword(), updateAdmin.getCredentialsSalt());
                        admin.setPassword(password);
                    } else {
                        admin.setPassword(updateAdmin.getPassword());
                    }
                    admin.setUpdatedAt(DateUtil.getCurrentTime());
                    adminService.save(admin);
                } else {
                    return ReturnUtil.error("操作失败", null, null);
                }
            }
            if (admin.getRoleId() != null) {
                adminRoleService.deleteAdminId(admin.getUid());
                for (String roleid : admin.getRoleId()) {
                    AdminRole adminRole = new AdminRole();
                    adminRole.setAdminId(admin.getUid());
                    adminRole.setRoleId(roleid);
                    adminRoleService.insert(adminRole);
                }
            } else {
                adminRoleService.deleteAdminId(admin.getUid());
            }

            // 更新用户权限
            adminShiroRealm.kickOutUser(admin.getUid(), false);

            return ReturnUtil.success("操作成功", null, "/console/admin/index");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnUtil.error("操作失败", null, null);
        }
    }

    @RequiresPermissions("admin:editpwd")
    @RequestMapping(value = "/savepwd", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap editPwd(String uid, String password) {
        try {
            if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(password)) {
                Admin admin = adminService.getById(uid);
                if (!"null".equals(admin)) {
                    String newPassword = PasswordUtil.createAdminPwd(password, admin.getSalt());
                    Admin pwdAdmin = new Admin();
                    pwdAdmin.setPassword(newPassword);
                    Example example = new Example(Admin.class);
                    example.createCriteria().andCondition("uid", uid);
                    adminService.updateExample(pwdAdmin, example);
                    return ReturnUtil.success("操作成功", null, null);
                } else {
                    return ReturnUtil.error("对像不存在，修改失败", null, null);
                }
            } else {
                return ReturnUtil.error("参数错误，修改失败", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.error("修改失败", null, null);
        }
    }

    @RequiresPermissions("admin:delete")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(String[] ids) {
        try {
            if (ids != null) {
                if (StringUtils.isNotBlank(ids.toString())) {
                    for (String id : ids) {
                        adminRoleService.deleteAdminId(id);
                        adminService.deleteById(id);
                        adminShiroRealm.kickOutUser(id, true);
                    }
                }
                return ReturnUtil.success("删除成功", null, null);
            } else {
                return ReturnUtil.error("删除失败", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.error("删除失败", null, null);
        }
    }

}
