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

package com.geekcattle.core.j2cache.autoconfigure;

import com.geekcattle.core.j2cache.cache.support.J2CacheCacheManger;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 开启对spring cache支持的配置入口
 * @author zhangsaizz
 */
@Configuration
@ConditionalOnClass(J2Cache.class)
@EnableConfigurationProperties({ J2CacheExtendConfig.class, CacheProperties.class })
@ConditionalOnProperty(name = "j2cache.open-spring-cache", havingValue = "true")
@EnableCaching
public class J2CacheSpringCacheAutoConfiguration {

    private final CacheProperties cacheProperties;

    private final J2CacheExtendConfig j2CacheExtendConfig;

    J2CacheSpringCacheAutoConfiguration(CacheProperties cacheProperties, J2CacheExtendConfig j2CacheExtendConfig) {
        this.cacheProperties = cacheProperties;
        this.j2CacheExtendConfig = j2CacheExtendConfig;
    }

    @Bean
    @ConditionalOnBean(CacheChannel.class)
    public J2CacheCacheManger cacheManager(CacheChannel cacheChannel) {
        List<String> cacheNames = cacheProperties.getCacheNames();
        J2CacheCacheManger cacheCacheManger = new J2CacheCacheManger(cacheChannel);
        cacheCacheManger.setAllowNullValues(j2CacheExtendConfig.isAllowNullValues());
        cacheCacheManger.setCacheNames(cacheNames);
        return cacheCacheManger;
    }

}
