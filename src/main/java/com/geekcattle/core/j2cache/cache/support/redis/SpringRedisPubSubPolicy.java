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

package com.geekcattle.core.j2cache.cache.support.redis;

import java.io.Serializable;
import java.util.Properties;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import net.oschina.j2cache.ClusterPolicy;
import net.oschina.j2cache.Command;
import com.geekcattle.core.j2cache.cache.support.util.SpringUtil;

/**
 * 使用spring redis实现订阅功能
 * @author zhangsaizz
 *
 */
public class SpringRedisPubSubPolicy implements ClusterPolicy{
	
	private RedisTemplate<String, Serializable> redisTemplate;
	
	private String channel = "j2cache_channel";

	@SuppressWarnings("unchecked")
	@Override
	public void connect(Properties props) {
		String channel_name = props.getProperty("jgroups.channel.name");
		if(channel_name != null && !channel_name.isEmpty()) {
			this.channel = channel_name;
		}
		this.redisTemplate = SpringUtil.getBean("j2CacheRedisTemplate", RedisTemplate.class);
		RedisMessageListenerContainer listenerContainer = SpringUtil.getBean("j2CacheRedisMessageListenerContainer", RedisMessageListenerContainer.class);
		listenerContainer.addMessageListener(new SpringRedisMessageListener(this, this.channel), new PatternTopic(this.channel));
	}

	@Override
	public void sendEvictCmd(String region, String... keys) {
		String com = new Command(Command.OPT_EVICT_KEY, region, keys).json();
        redisTemplate.convertAndSend(this.channel, com);	
	}

	@Override
	public void sendClearCmd(String region) {
		String com = new Command(Command.OPT_CLEAR_KEY, region, "").json();
		redisTemplate.convertAndSend(this.channel, com);	
	}

	@Override
	public void disconnect() {
		redisTemplate.convertAndSend(this.channel, Command.quit().json());
	}

	
}
