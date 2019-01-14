/*
 * Copyright (c) 2017-2018.  放牛极客<l_iupeiyu@qq.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * </p>
 *
 */

package com.geekcattle.controller;

import com.geekcattle.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping
    public String index(Model model) {
        return "home/index";
    }

    @RequestMapping(value = "/test",method = {RequestMethod.GET})
    public String test(Model model){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");




        String rq = DateUtil.getCurrentTime();
        System.out.println(rq);
        model.addAttribute("rq", rq);
        return "test/test";
    }

    @ResponseBody
    @RequestMapping(value = "/testPost",method = {RequestMethod.POST})
    public String testpost(HttpServletRequest request){
        return request.getParameter("rq");
    }


}
