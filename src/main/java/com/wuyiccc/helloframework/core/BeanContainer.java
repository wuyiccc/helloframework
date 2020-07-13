package com.wuyiccc.helloframework.core;

import com.wuyiccc.helloframework.aop.annotation.Aspect;
import com.wuyiccc.helloframework.core.annotation.Component;
import com.wuyiccc.helloframework.core.annotation.Controller;
import com.wuyiccc.helloframework.core.annotation.Repository;
import com.wuyiccc.helloframework.core.annotation.Service;
import com.wuyiccc.helloframework.util.ClassUtil;
import com.wuyiccc.helloframework.util.ValidationUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuyiccc
 * @date 2020/7/13 10:19
 * 岂曰无衣，与子同袍~
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {


    /**
     * 存放所有被配置标记的目标对象的Map
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class, Aspect.class);

    /**
     * 容器是否已经加载过bean
     */
    private boolean loaded = false;

    /**
     * 获取Bean容器实例
     *
     * @return BeanContainer
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * Bean实例数量
     *
     * @return
     */
    public int size() {
        return beanMap.size();
    }

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * 加载指定packageName包括其子包下的bean
     *
     * @param packageName
     */
    public synchronized void loadBeans(String packageName) {

        if (isLoaded()) {
            log.warn("BeanContainer has been loaded");
            return;
        }

        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName" + packageName);
        }

        for (Class<?> clazz : classSet) {

            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {

                if (clazz.isAnnotationPresent(annotation)) {
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                    break;
                }
            }
        }
        loaded = true;
    }


    /**
     * 添加一个class对象及其Bean实例
     *
     * @param clazz Class对象
     * @param bean  Bean实例
     * @return 原有的Bean实例，没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 删除一个IOC容器管理的对象
     *
     * @param clazz Class对象
     * @return 删除的Bean的实例，没有则返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取Bean实例
     *
     * @param clazz
     * @return
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }


    /**
     * 获取容器管理的所有Class对象集合
     *
     * @return
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有的Bean集合
     *
     * @return Bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }


    /**
     * 根据注解筛选出Bean的Class对象集合
     *
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {

        Set<Class<?>> keySet = getClasses();

        if (ValidationUtil.isEmpty(keySet)) {
            log.error("nothing in beanMap");
            return null;
        }

        // 通过注解筛选出符和条件的Class对象
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // 检查Class对象是否有相关的注解标记
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null; // 统一定义，如果size==0 ，那么返回null 而不是空的classSet
    }


    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括其本身
     * @param interfaceOrClass 接口Class或者父类Class
     * @return Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {

        // 获取beanMap中所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }

        // 判断ketSet里的元素是否是传入的接口的实现类或者是类的子类, 如果是，就添加到集合中
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) { // 判断interfaceOrClass是否是clazz父级别的类
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }




}
