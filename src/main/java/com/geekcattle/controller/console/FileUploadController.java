/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author geekcattle
 * date  2017/5/24 0024 下午 3:40.
 */
@Controller
@RequestMapping("/console/upload")
public class FileUploadController {

    @RequestMapping("/index")
    public String index(){
        return "console/file/index";
    }



}
