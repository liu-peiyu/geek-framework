/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.core;

import com.geekcattle.core.Interceptor.ResourceInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class WebMvcConfig implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceInterceptor resourceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("执行InterceptorRegistry");
        //registry.addInterceptor(resourceInterceptor);
        registry.addInterceptor(resourceInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
    }

}
