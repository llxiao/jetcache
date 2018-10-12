/**
 * Created on  13-09-09 18:16
 */
package com.alicp.jetcache;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public enum CacheResultCode {
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 部分成功
     */
    PART_SUCCESS,
    /**
     * 失败
     */
    FAIL,
    /**
     * 不存在
     */
    NOT_EXISTS,
    /**
     * 存在
     */
    EXISTS,
    /**
     * 已失效
     */
    EXPIRED
}
