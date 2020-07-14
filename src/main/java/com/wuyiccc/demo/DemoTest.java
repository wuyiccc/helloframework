package com.wuyiccc.demo;

import com.wuyiccc.demo.aop.AspectTarget;
import com.wuyiccc.demo.ioc.IocTest;
import com.wuyiccc.helloframework.aop.AspectWeaver;
import com.wuyiccc.helloframework.core.BeanContainer;
import com.wuyiccc.helloframework.injection.DependencyInjector;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyiccc
 * @date 2020/7/14 15:13
 * 岂曰无衣，与子同袍~
 */
@Slf4j
public class DemoTest {

    public static void main(String[] args) {

        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.wuyiccc.demo"); // 先加载IOC容器中的bean

        AspectTarget bean = (AspectTarget) beanContainer.getBean(AspectTarget.class);
        log.info(bean.getClass().getSimpleName());

        new AspectWeaver().doAop(); // 进行aop增强
        new DependencyInjector().doIoc(); // 最后再进行依赖注入，因为我们可能需要将增强后的对象注入到指定的属性上

        bean = (AspectTarget) beanContainer.getBean(AspectTarget.class);
        log.info(bean.getClass().getSimpleName());

        IocTest iocTest = (IocTest) beanContainer.getBean(IocTest.class);
        iocTest.test();

    }
}
