package com.geekcattle.controller.console;

import com.geekcattle.model.console.Log;
import com.geekcattle.service.console.LogService;
import com.geekcattle.util.ReturnUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author geekcattle
 */
@Controller
@RequestMapping("/console/log")
public class LogController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/log/index";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelMap list(Log log) {
        ModelMap map = new ModelMap();
        List<Log> lists = logService.getPageList(log);
        map.put("pageInfo", new PageInfo<Log>(lists));
        map.put("queryParam", log);
        return ReturnUtil.success("加载成功", map, null);
    }
}
