/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.service.common;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * author geekcattle
 * date 2017/3/22 0022 下午 15:38
 */
@Service
public interface  RedisService {

    public Jedis getResource();

    public void returnResource(Jedis jedis);

    public void set(String key, String value);

    public String get(String key);

}
