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

/**
 * @author geekcattle
 */
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
