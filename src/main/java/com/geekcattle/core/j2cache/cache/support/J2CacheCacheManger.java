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

package com.geekcattle.core.j2cache.cache.support;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.util.CollectionUtils;

import net.oschina.j2cache.CacheChannel;

/**
 * {@link Cache} implementation for J2Cache.
 * @author zhangsaizz
 *
 */
public class J2CacheCacheManger extends AbstractTransactionSupportingCacheManager {

	private boolean allowNullValues = true;

	private Collection<String> cacheNames;

	private boolean dynamic = true;

	private CacheChannel cacheChannel;

	public J2CacheCacheManger(CacheChannel cacheChannel){
		this.cacheChannel = cacheChannel;
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		Collection<Cache> caches = new LinkedHashSet<>(cacheNames.size());
		for (String name : cacheNames) {
			J2CacheCache cache = new J2CacheCache(name, cacheChannel, allowNullValues);
			caches.add(cache);
		}
		return caches;
	}


	public boolean isAllowNullValues() {
		return allowNullValues;
	}

	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	@Override
	protected Cache getMissingCache(String name) {
		return this.dynamic ? new J2CacheCache(name, cacheChannel, allowNullValues) : null;
	}


	public void setCacheNames(Collection<String> cacheNames) {
		Set<String> newCacheNames = CollectionUtils.isEmpty(cacheNames) ? Collections.<String> emptySet()
				: new HashSet<String>(cacheNames);
		this.cacheNames = newCacheNames;
		this.dynamic = newCacheNames.isEmpty();
	}

}
