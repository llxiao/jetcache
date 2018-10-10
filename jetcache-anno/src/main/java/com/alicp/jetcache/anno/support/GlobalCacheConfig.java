/**
 * Created on  13-09-09 17:29
 */
package com.alicp.jetcache.anno.support;

import com.alicp.jetcache.CacheBuilder;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class GlobalCacheConfig {
    /**
     * 使用@Cached和@CreateCache自动生成name的时候， 为了不让name太长，hiddenPackages指定的包名前缀被截掉
     */
    private String[] hiddenPackages;
    /**
     * 统计间隔，0表示不统计
     */
    protected int statIntervalMinutes;
    /**
     * jetcache-anno把cacheName作为远程缓存key前缀， 2.4.3以前的版本总是把areaName加在cacheName中，
     * 因此areaName也出现在key前缀中。2.4.4以后可以配置， 为了保持远程key兼容默认值为true，但是新项目的话false更合理些。
     * <pre>
     *     设置为false
     * </pre>
     */
    private boolean areaInCacheName = true;
    private boolean penetrationProtect = false;
    private boolean enableMethodCache = true;

    private Map<String, CacheBuilder> localCacheBuilders;
    private Map<String, CacheBuilder> remoteCacheBuilders;

    private ConfigProvider configProvider = new SpringConfigProvider();

    private CacheContext cacheContext;

    public GlobalCacheConfig() {
    }

    @PostConstruct
    public synchronized void init() {
        if (cacheContext == null) {
            cacheContext = configProvider.newContext(this);
            cacheContext.init();
        }
    }

    @PreDestroy
    public synchronized void shutdown() {
        if (cacheContext != null) {
            cacheContext.shutdown();
            cacheContext = null;
        }
    }

    public CacheContext getCacheContext() {
        return cacheContext;
    }

    public String[] getHiddenPackages() {
        return hiddenPackages;
    }

    public void setHiddenPackages(String[] hiddenPackages) {
        this.hiddenPackages = hiddenPackages;
    }

    public Map<String, CacheBuilder> getLocalCacheBuilders() {
        return localCacheBuilders;
    }

    public void setLocalCacheBuilders(Map<String, CacheBuilder> localCacheBuilders) {
        this.localCacheBuilders = localCacheBuilders;
    }

    public Map<String, CacheBuilder> getRemoteCacheBuilders() {
        return remoteCacheBuilders;
    }

    public void setRemoteCacheBuilders(Map<String, CacheBuilder> remoteCacheBuilders) {
        this.remoteCacheBuilders = remoteCacheBuilders;
    }

    public int getStatIntervalMinutes() {
        return statIntervalMinutes;
    }

    public void setStatIntervalMinutes(int statIntervalMinutes) {
        this.statIntervalMinutes = statIntervalMinutes;
    }

    public ConfigProvider getConfigProvider() {
        return configProvider;
    }

    public void setConfigProvider(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    public boolean isAreaInCacheName() {
        return areaInCacheName;
    }

    public void setAreaInCacheName(boolean areaInCacheName) {
        this.areaInCacheName = areaInCacheName;
    }

    public boolean isPenetrationProtect() {
        return penetrationProtect;
    }

    public void setPenetrationProtect(boolean penetrationProtect) {
        this.penetrationProtect = penetrationProtect;
    }

    public boolean isEnableMethodCache() {
        return enableMethodCache;
    }

    public void setEnableMethodCache(boolean enableMethodCache) {
        this.enableMethodCache = enableMethodCache;
    }
}
