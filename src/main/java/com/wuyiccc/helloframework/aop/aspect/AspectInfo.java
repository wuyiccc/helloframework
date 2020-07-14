package com.wuyiccc.helloframework.aop.aspect;

import com.wuyiccc.helloframework.aop.PointcutLocator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuyiccc
 * @date 2020/7/13 17:26
 * 岂曰无衣，与子同袍~
 */
@AllArgsConstructor
@Getter
public class AspectInfo {

    private int orderIndex;

    private DefaultAspect aspectObject;

    private PointcutLocator pointcutLocator;
}
