package com.wuyiccc.helloframework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author wuyiccc
 * @date 2020/7/14 8:24
 * 岂曰无衣，与子同袍~
 */
public abstract class DefaultAspect {

    /**
     * 事前拦截
     * @param targetClass 被代理的目标类
     * @param method 被代理的目标方法
     * @param args 被代理的目标方法对应的参数列表
     * @throws Throwable
     */
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {

    }

    /**
     * 事后拦截
     * @param targetClass 被代理的目标类
     * @param method 被代理的目标方法
     * @param args 被代理的目标方法对应的参数列表
     * @param returnValue 被代理的目标方法执行的返回值
     * @return
     * @throws Throwable
     */
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable{
        return returnValue;
    }

    /**
     * 发生异常之后的拦截方法
     * @param targetClass 被代理的目标类
     * @param method 被代理的目标方法
     * @param args 被代理的目标方法对应的参数列表
     * @param e 被代理的目标方法抛出的异常
     * @throws Throwable
     */
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {

    }




}
