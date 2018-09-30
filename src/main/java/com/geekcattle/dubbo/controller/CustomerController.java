package com.geekcattle.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.geekcattle.dubbo.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {


    //url = "dubbo://localhost:12345"    registry = "${dubbo.registry.address}"
    @Reference(url = "dubbo://localhost:12345", check = false)
    private DemoService demoService;

    @RequestMapping("/sayHello")
    public String sayHello() {
        return demoService.sayHello("dubbo");
    }

}
