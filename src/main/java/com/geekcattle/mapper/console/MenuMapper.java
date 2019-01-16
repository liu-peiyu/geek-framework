package com.geekcattle.mapper.console;

import com.geekcattle.model.console.Menu;
import com.geekcattle.core.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author geekcattle
 */
@Service
public interface MenuMapper extends CustomerMapper<Menu> {

    /**
     * 获取某一个用户的菜单编码
     * @param userId
     * @return
     */
    Set<String> findMenuCodeByUserId(String userId);

    /**
     * 获取所有菜单编码
     * @return
     */
    Set<String> getALLMenuCode();

    /**
     * 根据用户ID获取菜单
     * @param userId
     * @return
     */
    List<Menu> selectMenuByAdminId(String userId);

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> selectAllMenu();

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    List<Menu> selectMenuByRoleId(String roleId);
}