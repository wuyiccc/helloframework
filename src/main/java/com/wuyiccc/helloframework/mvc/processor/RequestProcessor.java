package com.wuyiccc.helloframework.mvc.processor;

import com.wuyiccc.helloframework.mvc.RequestProcessorChain;

/**
 * @author wuyiccc
 * @date 2020/7/14 22:39
 * 岂曰无衣，与子同袍~
 */
public interface RequestProcessor {


    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
