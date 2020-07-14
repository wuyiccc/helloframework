package com.wuyiccc.demo.ioc;

import com.wuyiccc.demo.aop.AspectTarget;
import com.wuyiccc.helloframework.core.annotation.Component;
import com.wuyiccc.helloframework.injection.annotation.Autowired;

/**
 * @author wuyiccc
 * @date 2020/7/14 16:21
 * 岂曰无衣，与子同袍~
 */
@Component
public class IocTest {

    @Autowired
    private AspectTarget aspectTarget;

    public void test() {
        aspectTarget.testRight();
        aspectTarget.testThrowing();
    }


}
