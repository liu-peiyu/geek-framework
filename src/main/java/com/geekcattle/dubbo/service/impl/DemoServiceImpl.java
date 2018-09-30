package com.geekcattle.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.geekcattle.dubbo.service.DemoService;

@Service(
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
