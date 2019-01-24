package com.geekcattle.service.console;

import com.geekcattle.mapper.console.RoleMapper;
import com.geekcattle.model.console.Role;
import com.geekcattle.util.CamelCaseUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

/**
 * @author geekcattle
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getPageList(Role role) {
        PageHelper.offsetPage(role.getOffset(), role.getLimit());
        PageHelper.orderBy(CamelCaseUtil.toUnderlineName(role.getSort())+" "+role.getOrder());
        return roleMapper.selectAll();
    }

    public List<Role> getFromAll(){
        Example example = new Example(Role.class);
        example.createCriteria()
                .andCondition("enable = ", 1);
        return roleMapper.selectByExample(example);
    }

    public Integer getCount(Example example){
        return roleMapper.selectCountByExample(example);
    }

    public Role getById(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> getById(Role roles) {
        return roleMapper.select( roles);
    }

    public void deleteById(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    public void save(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    public void insert(Role role){
        roleMapper.insert(role);
    }

    public Set<String> findRoleByUserId(String userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    public List<Role> selectRoleListByAdminId(String id){
        return roleMapper.selectRoleListByAdminId(id);
    }

}
