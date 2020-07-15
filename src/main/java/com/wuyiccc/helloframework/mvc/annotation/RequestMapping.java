package com.wuyiccc.helloframework.mvc.annotation;

import com.wuyiccc.helloframework.mvc.type.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuyiccc
 * @date 2020/7/14 22:31
 * 岂曰无衣，与子同袍~
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    // 请求路径
    String value() default "";

    //请求方法
    RequestMethod method() default RequestMethod.GET;
}
