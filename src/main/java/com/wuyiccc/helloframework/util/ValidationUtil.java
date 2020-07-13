package com.wuyiccc.helloframework.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/7/13 10:41
 * 岂曰无衣，与子同袍~
 */
public class ValidationUtil {

    /**
     * String是否为null或者""
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj) {
        return obj == null || "".equals(obj);
    }


    /**
     * java的数组是否为null或者size=0
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }


    /**
     * 判断Collection是否为null或者size=0
     *
     * @param obj
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * Map是否为null 或 size 为0
     * @param obj
     * @return
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }
}
