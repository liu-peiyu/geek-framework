package com.geekcattle.mapper.console;

import com.geekcattle.model.console.Role;
import com.geekcattle.util.CustomerMapper;

import java.util.List;
import java.util.Set;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:32
 */
public interface RoleMapper extends CustomerMapper<Role> {
    Set<String> findRoleByUserId(String userId);
    List<Role> selectRoleListByAdminId(String Id);
}
