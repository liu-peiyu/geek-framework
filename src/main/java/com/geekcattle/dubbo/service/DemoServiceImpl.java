package com.geekcattle.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.geekcattle.dubbo.DemoService;

@Service(
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DemoServiceImpl implements DemoService {

    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
