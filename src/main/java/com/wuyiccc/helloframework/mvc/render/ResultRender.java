package com.wuyiccc.helloframework.mvc.render;

import com.wuyiccc.helloframework.mvc.RequestProcessorChain;

/**
 * @author wuyiccc
 * @date 2020/7/15 7:52
 * 岂曰无衣，与子同袍~
 */
public interface ResultRender {

    //执行渲染
    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
