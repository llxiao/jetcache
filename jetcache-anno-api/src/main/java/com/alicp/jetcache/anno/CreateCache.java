package com.alicp.jetcache.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2016/12/9.
 *
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CreateCache {
    /**
     * <pre>
     * 	如果需要连接多个缓存系统，可在配置多个cache area，
     * 	这个属性指定要使用的那个area的name
     *
     * If you want to use multi backend cache system, you can setup multi "cache area" in configuration,
     * this attribute specifies the name of the "cache area" you want to use.
     * </pre>
     *
     * @return the name of cache area
     */
    String area() default CacheConsts.DEFAULT_AREA;

    /**
     * <pre>
     *  指定缓存的名称，不是必须的，如果没有指定，会使用类名+方法名。name会被用于远程缓存的key前缀。
     *  另外在统计中，一个简短有意义的名字会提高可读性。如果两个@CreateCache的name和area相同，
     *  它们会指向同一个Cache实例
     * </pre>
     * The name of this Cache instance, optional. If you do not specify, JetCache will auto generate
     * one. The name is used to display statistics information and as part of key prefix when using
     * a remote cache. If two @CreateCache have same name and area, they will point to same Cache
     * instance.
     *
     * @return the cache name
     */
    String name() default CacheConsts.UNDEFINED_STRING;

    /**
     * 指定expire的单位 Specify the time unit of expire.
     *
     * @return the time unit of expire
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * <pre>
     * 该Cache实例的默认超时时间定义，注解上没有定义的时候会使用全局配置，
     * 如果此时全局配置也没有定义，则取无穷大
     * </pre>
     * The default expire time of this Cache instance. Use global config if the attribute value is
     * absent, and if the global config is not defined either, use infinity.
     *
     * @return the default expire time
     */
    int expire() default CacheConsts.UNDEFINED_INT;

    /**
     * Use to specify the local cache expire time when cacheType=CacheType.BOTH, use "expire" if
     * absent.
     *
     * @return the local cache expire time
     */
    int localExpire() default CacheConsts.UNDEFINED_INT;

    /**
     * <pre>
     * 缓存的类型，包括CacheType.REMOTE、CacheType.LOCAL、CacheType.BOTH。
     * 如果定义为BOTH，会使用LOCAL和REMOTE组合成两级缓存
     * </pre>
     * Type of the Cache instance. May be CacheType.REMOTE, CacheType.LOCAL, CacheType.BOTH. Use two
     * level cache (local+remote) when value is CacheType.BOTH.
     *
     * @return the cache type
     */
    CacheType cacheType() default CacheType.REMOTE;

    /**
     * <pre>
     *  如果cacheType为CacheType.LOCAL或CacheType.BOTH，这个参数指定本地缓存的最大元素数量，
     *  以控制内存占用。注解上没有定义的时候会使用全局配置，如果此时全局配置也没有定义，则取100
     * </pre>
     * Specify max elements in local memory when cacheType is CacheType.LOCAL or CacheType.BOTH. Use
     * global config if the attribute value is absent, and if the global config is not defined
     * either, use CacheConsts.DEFAULT_LOCAL_LIMIT instead.
     *
     * @return local maximal elements of the LOCAL/BOTH cache
     */
    int localLimit() default CacheConsts.UNDEFINED_INT;

    /**
     * <pre>
     *  如果cacheType为CacheType.REMOTE或CacheType.BOTH，指定远程缓存的序列化方式。JetCache内置的可选值为
     *  SerialPolicy.JAVA和SerialPolicy.KRYO。注解上没有定义的时候会使用全局配置，如果此时全局配置也没有定义，
     *  则取SerialPolicy.JAVA
     * </pre>
     * Specify the serialization policy of remote cache when cacheType is CacheType.REMOTE or
     * CacheType.BOTH. The JetCache build-in serialPolicy are SerialPolicy.JAVA or
     * SerialPolicy.KRYO. Use global config if the attribute value is absent, and if the global
     * config is not defined either, use SerialPolicy.JAVA instead.
     *
     * @return the serialization policy name of cache value
     */
    String serialPolicy() default CacheConsts.UNDEFINED_STRING;

    /**
     * <pre>
     *  指定KEY的转换方式，用于将复杂的KEY类型转换为缓存实现可以接受的类型，JetCache内置的可选值为
     *  KeyConvertor.FASTJSON和KeyConvertor.NONE。NONE表示不转换，FASTJSON通过fastjson将复杂
     *  对象KEY转换成String。如果注解上没有定义，则使用全局配置。
     * </pre>
     * Specify the key convertor. Used to convert the complex key object. The JetCache build-in
     * keyConvertor are KeyConvertor.FASTJSON or KeyConvertor.NONE. NONE indicate do not convert,
     * FASTJSON will use fastjson to convert key object to a string. Use global config if the
     * attribute value is absent.
     *
     * @return convertor name of cache key
     */
    String keyConvertor() default CacheConsts.UNDEFINED_STRING;
}
