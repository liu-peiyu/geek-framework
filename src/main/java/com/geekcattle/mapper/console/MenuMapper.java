/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.mapper.console;

import com.geekcattle.model.console.Menu;
import com.geekcattle.util.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MenuMapper extends CustomerMapper<Menu> {
    Set<String> findMenuCodeByUserId(String userId);
    Set<String> getALLMenuCode();
    List<Menu> selectMenuByAdminId(String userId);
    List<Menu> selectAllMenu();
    List<Menu> selectMenuByRoleId(String roleId);
}