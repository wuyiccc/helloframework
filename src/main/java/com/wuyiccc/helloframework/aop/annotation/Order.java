package com.wuyiccc.helloframework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuyiccc
 * @date 2020/7/13 17:25
 * 岂曰无衣，与子同袍~
 */
// aspect的优先级，值越小，优先级越高
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    int value();
}
