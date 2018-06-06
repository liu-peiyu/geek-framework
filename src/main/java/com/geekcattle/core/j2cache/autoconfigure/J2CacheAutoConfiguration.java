package com.geekcattle.core.j2cache.autoconfigure;

import com.geekcattle.core.j2cache.cache.support.util.SpringUtil;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;

@ConditionalOnClass(J2Cache.class)
@EnableConfigurationProperties({J2CacheConfig.class})
@Configuration
public class J2CacheAutoConfiguration {

    @Bean
    @DependsOn("springUtil")
    public CacheChannel cacheChannel() throws IOException {
        J2CacheConfig cacheConfig = new J2CacheConfig();
        J2CacheBuilder builder = J2CacheBuilder.init(cacheConfig);
        return builder.getChannel();
    }

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

}
