package com.service.impl;
import javax.annotation.Resource;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

/** 
 * @ClassName: CacheServiceImpl 
 * @Description: TODO缓存
 * @author: zhang.wei1
 * @date: 2016年9月23日 下午1:32:14  
 */
@Service
public class CacheServiceImpl {

	@Resource
	private EhCacheCacheManager cacheManager;

	
	public void putCenter_Msg(String key, String value) throws Exception {
		if(key==null || key.length()==0){
			return;
		}

		Cache cache = cacheManager.getCache("CACHE.CENTER_RSP");
		cache.put(key, value);
	}

	public String getCenter_Msg(String key) throws Exception {
		if(key==null){
			return null;
		}

		Cache cache = cacheManager.getCache("CACHE.CENTER_RSP");

		return cache.get(key, String.class);
	}

}
