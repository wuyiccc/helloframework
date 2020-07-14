package com.wuyiccc.helloframework.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wuyiccc
 * @date 2020/7/14 9:03
 * 岂曰无衣，与子同袍~
 */
public class AspectListExecutor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return null;
    }
}
