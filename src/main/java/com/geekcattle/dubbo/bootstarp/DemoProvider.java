package com.geekcattle.dubbo.bootstarp;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DemoProvider {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoProvider.class).web(WebApplicationType.NONE).run(args);
    }
}
