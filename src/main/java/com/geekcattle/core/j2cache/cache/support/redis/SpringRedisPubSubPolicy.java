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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import net.oschina.j2cache.cluster.ClusterPolicy;
import net.oschina.j2cache.Command;
import net.oschina.j2cache.J2CacheConfig;

import com.geekcattle.core.j2cache.cache.support.util.SpringUtil;
import com.geekcattle.core.j2cache.autoconfigure.J2CacheExtendConfig;

/**
 * 使用spring redis实现订阅功能
 * @author zhangsaizz
 *
 */
public class SpringRedisPubSubPolicy implements ClusterPolicy {
	
	private RedisTemplate<String, Serializable> redisTemplate;

	private J2CacheExtendConfig config;

	/**
	 * 是否是主动模式
	 */
	private static boolean isActive = false;
	
	private String channel = "j2cache_channel";

	@SuppressWarnings("unchecked")
	@Override
	public void connect(Properties props) {
		J2CacheConfig j2config = SpringUtil.getBean(J2CacheConfig.class);
		this.config =  SpringUtil.getBean(J2CacheExtendConfig.class);
		this.redisTemplate = SpringUtil.getBean("j2CacheRedisTemplate", RedisTemplate.class);
		if("active".equals(config.getCacheCleanMode())) {
			isActive = true;
		}
		String channel_name = j2config.getL2CacheProperties().getProperty("channel");
		if(channel_name != null && !channel_name.isEmpty()) {
			this.channel = channel_name;
		}
		RedisMessageListenerContainer listenerContainer = SpringUtil.getBean("j2CacheRedisMessageListenerContainer", RedisMessageListenerContainer.class);

		listenerContainer.addMessageListener(new SpringRedisMessageListener(this, this.channel), new PatternTopic(this.channel));
		if(isActive || "blend".equals(config.getCacheCleanMode())) {
			//设置键值回调
			ConfigureNotifyKeyspaceEventsAction action = new ConfigureNotifyKeyspaceEventsAction();
			action.config(listenerContainer.getConnectionFactory().getConnection());

			String namespace = 	j2config.getL2CacheProperties().getProperty("namespace");
			String database = j2config.getL2CacheProperties().getProperty("database");
			String expired  = "__keyevent@" + (database == null || "".equals(database) ? "0" : database) + "__:expired";
			String del = "__keyevent@" + (database == null || "".equals(database) ? "0" : database) + "__:del";
			List<PatternTopic> topics = new ArrayList<>();
			topics.add(new PatternTopic(expired));
			topics.add(new PatternTopic(del));
			listenerContainer.addMessageListener(new SpringRedisActiveMessageListener(this, namespace), topics);
		}
	}

	@Override
	public void publish(Command cmd) {
		if(!isActive || "blend".equals(config.getCacheCleanMode())) {
			redisTemplate.convertAndSend(this.channel, cmd.json());
		}
	}

	@Override
	public void disconnect() {
		redisTemplate.convertAndSend(this.channel, Command.quit().json());
	}

	@Override
	public void evict(String region, String... keys) {
		if(!isActive || "blend".equals(config.getCacheCleanMode())) {
			String com = new Command(Command.OPT_EVICT_KEY, region, keys).json();
			redisTemplate.convertAndSend(this.channel, com);
		}
	}

	@Override
	public void clear(String region) {
		if(!isActive || "blend".equals(config.getCacheCleanMode())) {
			String com = new Command(Command.OPT_CLEAR_KEY, region, "").json();
			redisTemplate.convertAndSend(this.channel, com);
		}
	}

	@Override
	public void handleCommand(Command cmd) {
		redisTemplate.convertAndSend(this.channel, Command.quit().json());
	}


}
