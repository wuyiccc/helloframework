package com.wuyiccc.helloframework.injection;

import com.wuyiccc.helloframework.core.BeanContainer;
import com.wuyiccc.helloframework.injection.annotation.Autowired;
import com.wuyiccc.helloframework.util.ClassUtil;
import com.wuyiccc.helloframework.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author wuyiccc
 * @date 2020/7/13 14:57
 * 岂曰无衣，与子同袍~
 */
@Slf4j
public class DependencyInjector {

    /**
     * Bean容器
     */
    private BeanContainer beanContainer;


    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行IOC
     */
    public void doIoc() {

        if (ValidationUtil.isEmpty(beanContainer.getClasses())) {
            log.warn("empty classes in BeanContainer");
            return;
        }

        // 遍历容器中的所有class的对象
        for (Class<?> clazz : beanContainer.getClasses()) {

            Field[] fields = clazz.getDeclaredFields();

            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }

            for (Field field : fields) {
                // 找出被Autowired标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value(); // value值
                    // 获取该field的类型
                    Class<?> fieldClass = field.getType();
                    // 获取该类型在容器中对应的实例
                    Object fieldValue = getFieldInstance(fieldClass, autowiredValue);
                    if (fieldValue == null) {
                        throw new RuntimeException("unable to inject relevant type, target fieldClass is:" + fieldClass.getName() + " autowiredValue is : " + autowiredValue);
                    } else {
                        // 通过反射将对应的成员变量实例注入到成员变量所在类的实例里
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldValue, true);
                    }
                }

            }

        }


    }

    /**
     * 根据Class在beanContainer里获取实例或者实现类
     *
     * @param fieldClass     实例中属性的类型
     * @param autowiredValue 要注入的值的名称
     * @return
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        // 看能否获取同等级的实例
        Object fieldValue = beanContainer.getBean(fieldClass);
        if (fieldValue != null) {
            return fieldValue;
        } else {
            Class<?> implementedClass = geImplementedClass(fieldClass, autowiredValue);
            if (implementedClass != null) {
                return beanContainer.getBean(implementedClass);
            } else {
                return null;
            }
        }
    }

    /**
     * 获取接口的实现类
     *
     * @param fieldClass 实例中要注入的属性的类型
     * @return
     */
    private Class<?> geImplementedClass(Class<?> fieldClass, String autowiredValue) {

        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);
        if (!ValidationUtil.isEmpty(classSet)) {
            if (ValidationUtil.isEmpty(autowiredValue)) {

                if (classSet.size() == 1) {
                    return classSet.iterator().next();
                } else {
                    throw new RuntimeException("multiple implemented classes for " + fieldClass.getName() + "please set @Autowired's value to pick one");
                }
            } else {

                for (Class<?> clazz : classSet) {
                    if (autowiredValue.equals(clazz.getSimpleName())) {
                        return clazz;
                    }
                }
            }
        }

        return null;
    }
}
