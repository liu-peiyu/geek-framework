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

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "j2cache")
public class J2CacheExtendConfig {

    private String configLocation = "/j2cache.properties";

    /**
     * 是否开启spring cache缓存,注意:开启后需要添加spring.cache.type=none,将缓存类型设置为none
     */
    private Boolean openSpringCache = false;

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public Boolean getOpenSpringCache() {
        return openSpringCache;
    }

    public void setOpenSpringCache(Boolean openSpringCache) {
        this.openSpringCache = openSpringCache;
    }
}
