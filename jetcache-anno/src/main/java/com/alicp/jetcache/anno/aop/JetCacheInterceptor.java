/**
 * Created on  13-09-18 20:33
 */
package com.alicp.jetcache.anno.aop;

import com.alicp.jetcache.anno.method.CacheHandler;
import com.alicp.jetcache.anno.method.CacheInvokeConfig;
import com.alicp.jetcache.anno.method.CacheInvokeContext;
import com.alicp.jetcache.anno.support.ConfigMap;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * <pre>jetcache拦截器</pre>
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class JetCacheInterceptor implements MethodInterceptor, ApplicationContextAware {

    //private static final Logger logger = LoggerFactory.getLogger(JetCacheInterceptor.class);

    private ConfigMap cacheConfigMap;
    private ApplicationContext applicationContext;
    /**
     * 全局配置信息
     */
    GlobalCacheConfig globalCacheConfig;
    /**
     * 设置context
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {

        //从context 中获取全局配置 如果全局配置为空 或者 只支持方法缓存 则不拦截 直接执行
        if (globalCacheConfig == null) {
            globalCacheConfig = applicationContext.getBean(GlobalCacheConfig.class);
        }
        if (globalCacheConfig == null || !globalCacheConfig.isEnableMethodCache()) {
            return invocation.proceed();
        }
        //获取缓存方法
        Method method = invocation.getMethod();
        //获取具体的实现类的实例
        Object obj = invocation.getThis();
        // 获取缓存方法
        CacheInvokeConfig cac = null;
        //实现类不为空
        if (obj != null) {
            //key 为方法名 返回值 实现类
            //jetcache.samples.spring.UserService.loadUser(J)Ljetcache/samples/spring/User;_jetcache.samples.spring.UserServiceImpl
            String key = CachePointcut.getKey(method, obj.getClass());
            cac  = cacheConfigMap.getByMethodInfo(key);
        }

        //缓存方法的配置为空 或者 配置没有缓存方法实例 直接执行
        if (cac == null || cac == CacheInvokeConfig.getNoCacheInvokeConfigInstance()) {
            return invocation.proceed();
        }

        CacheInvokeContext context = globalCacheConfig.getCacheContext().createCacheInvokeContext(cacheConfigMap);
        context.setTargetObject(invocation.getThis());
        context.setInvoker(invocation::proceed);
        context.setMethod(method);
        context.setArgs(invocation.getArguments());
        context.setCacheInvokeConfig(cac);
        context.setHiddenPackages(globalCacheConfig.getHiddenPackages());
        return CacheHandler.invoke(context);
    }

    public void setCacheConfigMap(ConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }

}
