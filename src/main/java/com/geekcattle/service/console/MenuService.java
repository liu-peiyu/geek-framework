package com.geekcattle.service.console;

import com.geekcattle.mapper.console.MenuMapper;
import com.geekcattle.model.console.Menu;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author geekcattle
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> getPageList(Menu menu) {
        PageHelper.offsetPage(menu.getOffset(), menu.getLimit());
        PageHelper.orderBy("listorder asc, created_at desc");
        return menuMapper.selectAll();
    }

    public List<Menu> getMenuAll(){
        PageHelper.orderBy("listorder asc, created_at desc");
        return menuMapper.selectAll();
    }

    public List<Menu> getMenuList(Example example){
        PageHelper.orderBy("listorder asc, created_at desc");
        return menuMapper.selectByExample(example);
    }

    public List<Menu> selectByParentMenuList(String parentId){
        Example example = new Example(Menu.class);
        example.createCriteria().andCondition("parent_id = ", parentId);
        PageHelper.orderBy("listorder asc, created_at desc");
        return menuMapper.selectByExample(example);
    }


    public Integer getCount(Example example){
        return menuMapper.selectCountByExample(example);
    }

    public Menu getById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public void deleteById(String id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    public void insert(Menu menu){
        menuMapper.insert(menu);
    }

    public void save(Menu menu) {
        if (menu.getMenuId() != null) {
            menuMapper.updateByPrimaryKey(menu);
        } else {
            menuMapper.insert(menu);
        }
    }

    public Set<String> findMenuCodeByUserId(String userId) {
        return menuMapper.findMenuCodeByUserId(userId);
    }

    public Set<String> getAllMenuCode() {
        return menuMapper.getALLMenuCode();
    }

    public List<Menu> getComboTree(Menu menu) {
        return menuMapper.selectAll();
    }

    public List<Menu> selectMenuByAdminId(String userId){
        return menuMapper.selectMenuByAdminId(userId);
    }

    public List<Menu> selectAllMenu(){
        return menuMapper.selectAllMenu();
    }

    public List<Menu> selectMenuByRoleId(String roleId){
        return menuMapper.selectMenuByRoleId(roleId);
    }

    public void update(Menu menu, Example example ){
        menuMapper.updateByExampleSelective(menu, example);
    }

    public List<Menu> getChildMenuList(ArrayList<Menu> menuLists ,String parentId){
        Example example = new Example(Menu.class);
        example.createCriteria().andCondition("parent_id = ", parentId);
        PageHelper.orderBy("listorder asc, created_at desc");
        List<Menu> list = menuMapper.selectByExample(example);
        for(Menu menu : list){
            menuLists.add(menu);
            /*if(menu.getChildNum() > 0){

            }*/
            getChildMenuList(menuLists, menu.getMenuId());

        }
        return menuLists;
    }

}
