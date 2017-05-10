package com.geekcattle.mapper.console;

import com.geekcattle.model.console.Admin;
import com.geekcattle.util.CustomerMapper;

import java.util.List;
import java.util.Optional;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:32
 */
public interface AdminMapper extends CustomerMapper<Admin> {
    Admin selectByUsername(String username);
    void deleteById(String Id);
}
