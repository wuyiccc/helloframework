package com.wuyiccc.helloframework.mvc.render.impl;

import com.wuyiccc.helloframework.mvc.RequestProcessorChain;
import com.wuyiccc.helloframework.mvc.render.ResultRender;

/**
 * @author wuyiccc
 * @date 2020/7/15 7:51
 * 岂曰无衣，与子同袍~
 */
public class DefaultResultRender  implements ResultRender {


    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {

        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getResponseCode());
    }
}
