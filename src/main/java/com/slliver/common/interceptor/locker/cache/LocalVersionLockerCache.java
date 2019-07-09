package com.slliver.common.interceptor.locker.cache;

import com.slliver.common.interceptor.locker.annotation.VersionLocker;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/1/31 17:52
 * @version: 1.0
 */
public class LocalVersionLockerCache implements VersionLockerCache {

    private static final Log log = LogFactory.getLog(LocalVersionLockerCache.class);
    private ConcurrentHashMap<String, ConcurrentHashMap<MethodSignature, VersionLocker>> caches = new ConcurrentHashMap<>();

    @Override
    public boolean containMethodSignature(MethodSignature vm) {
        String nameSpace = getNameSpace(vm);
        ConcurrentHashMap<MethodSignature, VersionLocker> cache = caches.get(nameSpace);
        if (null == cache || cache.isEmpty()) {
            return false;
        }
        boolean containsMethodSignature = cache.containsKey(vm);
        if (containsMethodSignature && log.isDebugEnabled()) {
            log.debug("The method " + nameSpace + vm.getId() + "is hit in cache.");
        }
        return containsMethodSignature;
    }

    // 这里去掉synchronized或者重入锁，因为这里的操作满足幂等性
    @Override
    public void cacheMethod(MethodSignature vm, VersionLocker locker) {
        String nameSpace = getNameSpace(vm);
        ConcurrentHashMap<MethodSignature, VersionLocker> cache = caches.get(nameSpace);
        if (null == cache || cache.isEmpty()) {
            cache = new ConcurrentHashMap<>();
            cache.put(vm, locker);
            caches.put(nameSpace, cache);
            if (log.isDebugEnabled()) {
                log.debug("Locker debug info ==> " + nameSpace + ": " + vm.getId() + " is cached.");
            }
        } else {
            cache.put(vm, locker);
        }
    }

    @Override
    public VersionLocker getVersionLocker(MethodSignature vm) {
        String nameSpace = getNameSpace(vm);
        ConcurrentHashMap<MethodSignature, VersionLocker> cache = caches.get(nameSpace);
        if (null == cache || cache.isEmpty()) {
            return null;
        }
        return cache.get(vm);
    }

    private String getNameSpace(MethodSignature vm) {
        String id = vm.getId();
        int pos = id.lastIndexOf(".");
        return id.substring(0, pos);
    }
}
