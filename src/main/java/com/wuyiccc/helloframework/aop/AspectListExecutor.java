package com.wuyiccc.helloframework.aop;

import com.wuyiccc.helloframework.aop.aspect.AspectInfo;
import com.wuyiccc.helloframework.util.ValidationUtil;
import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/7/14 9:03
 * 岂曰无衣，与子同袍~
 */
public class AspectListExecutor implements MethodInterceptor {

    private Class<?> targetClass;

    @Getter
    private List<AspectInfo> sortedAspectInfoList;


    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> sortedAspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(sortedAspectInfoList);
    }

    /**
     * 按照order的值进行升序排序，确保Order值小的aspect先被织入
     *
     * @param aspectInfoList
     * @return
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        // 升序排列
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Object returnValue = null;

        collectAccurateMatchedAspectList(method);

        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return methodProxy.invokeSuper(proxy, args);
        }
        // 1. 按照order的顺序升序执行完所有Aspect的before方法
        invokeBeforeAdvices(method, args);
        try {
            // 2. 执行被代理类的方法
            returnValue = methodProxy.invokeSuper(proxy, args);
            // 3. 如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
            invokeAfterReturningAdvices(method, args, returnValue);

        } catch (Exception e) {
            // 4. 如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
            invokeAfterThrowingAdvices(method, args, e);
        }
        return returnValue;
    }

    private void collectAccurateMatchedAspectList(Method method) {
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return;
        }
        Iterator<AspectInfo> it = sortedAspectInfoList.iterator();
        while (it.hasNext()) {
            AspectInfo aspectInfo = it.next();
            if (!aspectInfo.getPointcutLocator().accurateMatches(method)) {
                it.remove(); // 需要用Iterator自带的remove，不能用sortedAspectInfoList的remove，否则就会出现并发修改异常
            }
        }
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable {

        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }

    private void invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {

        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
    }

    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {

        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }
}
