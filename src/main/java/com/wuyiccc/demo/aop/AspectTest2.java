package com.wuyiccc.demo.aop;

import com.wuyiccc.helloframework.aop.annotation.Aspect;
import com.wuyiccc.helloframework.aop.annotation.Order;
import com.wuyiccc.helloframework.aop.aspect.DefaultAspect;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author wuyiccc
 * @date 2020/7/14 15:33
 * 岂曰无衣，与子同袍~
 */
@Order(2)
@Slf4j
//@Aspect(pointcut = "within(com.wuyiccc.demo.aop.*)")
@Aspect(pointcut = "execution(* com.wuyiccc.demo.aop..*.*(..))")
public class AspectTest2 extends DefaultAspect {

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("AspectTest2#before()方法执行了");
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {

        log.info("AspectTest2#afterReturning()方法执行了");

        return returnValue;
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
        log.info("AspectTest2#afterThrowing()方法执行了");
    }

}
