package com.wuyiccc.helloframework.mvc.render.impl;

import com.wuyiccc.helloframework.mvc.RequestProcessorChain;
import com.wuyiccc.helloframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyiccc
 * @date 2020/7/15 7:53
 * 岂曰无衣，与子同袍~
 */
public class InternalErrorResultRender implements ResultRender {
    private String errorMsg;

    public InternalErrorResultRender(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {

        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMsg);
    }

}
