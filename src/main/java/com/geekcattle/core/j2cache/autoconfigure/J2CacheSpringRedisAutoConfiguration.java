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

import com.geekcattle.core.j2cache.cache.support.util.J2CacheSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * 对spring redis支持的配置入口
 * @author zhangsaizz
 *
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@AutoConfigureBefore({J2CacheAutoConfiguration.class})
public class J2CacheSpringRedisAutoConfiguration {

    @Bean("j2CacheRedisTemplate")
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, Serializable> j2CacheRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new J2CacheSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean("j2CacheRedisMessageListenerContainer")
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

}
