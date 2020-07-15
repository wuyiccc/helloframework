package com.wuyiccc.helloframework.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuyiccc
 * @date 2020/7/14 22:32
 * 岂曰无衣，与子同袍~
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    // 方法参数名称
    String value() default "";

    // 该参数是否是必须的
    boolean required() default true;
}
