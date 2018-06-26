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

import com.geekcattle.core.j2cache.cache.support.util.SpringUtil;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;

@ConditionalOnClass(J2Cache.class)
@EnableConfigurationProperties({J2CacheExtendConfig.class})
@Configuration
public class J2CacheAutoConfiguration {

    @Autowired
    private J2CacheExtendConfig cacheExtendConfig;

    @Bean
    @DependsOn("springUtil")
    public CacheChannel cacheChannel() throws IOException {
        J2CacheConfig cacheConfig = new J2CacheConfig();
        cacheConfig = cacheConfig.initFromConfig(cacheExtendConfig.getConfigLocation());
        J2CacheBuilder builder = J2CacheBuilder.init(cacheConfig);
        return builder.getChannel();
    }

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

}
