package com.wuyiccc.helloframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author wuyiccc
 * @date 2020/7/14 9:04
 * 岂曰无衣，与子同袍~
 */
public class ProxyCreator {

    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor) {
        return Enhancer.create(targetClass, methodInterceptor);
    }
}
