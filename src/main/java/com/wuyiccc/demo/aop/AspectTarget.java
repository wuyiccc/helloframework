package com.wuyiccc.demo.aop;

import com.wuyiccc.helloframework.core.annotation.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyiccc
 * @date 2020/7/14 15:39
 * 岂曰无衣，与子同袍~
 */
@Component
@Slf4j
public class AspectTarget {

    public void testRight() {
        log.info("AspectTarget#Right()方法执行！");
    }


    public void testThrowing() {
        log.info("AspectTarget#testThrowing()方法执行");
        throw new RuntimeException();
    }

}
