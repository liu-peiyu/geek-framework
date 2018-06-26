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

package com.geekcattle.core.shiro;

import com.geekcattle.core.redis.RedisConfiguration;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


@Configuration
public class CustomSessionListener implements SessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisConfiguration redisConfiguration;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        logger.info("onStart:{}", session.getId());
    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        logger.info("onStop:{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("onExpiration:{}", session.getId());
        redisTemplate.delete(redisConfiguration.getSessionPrefix() + session.getId().toString());
    }

}

