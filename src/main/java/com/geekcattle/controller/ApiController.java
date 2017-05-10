package com.geekcattle.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("")
    public String getIndex(){
        return "welcome spring boot!";
    }

    @RequestMapping("/test")
    public ModelMap Test(){
        ModelMap result = new ModelMap();
        result.put("msg", "删除成功!");
        result.put("status",1);
        return result;
    }
}
