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
