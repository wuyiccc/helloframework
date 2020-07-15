package com.wuyiccc.helloframework.mvc.render.impl;

import com.wuyiccc.helloframework.mvc.RequestProcessorChain;
import com.wuyiccc.helloframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyiccc
 * @date 2020/7/15 7:54
 * 岂曰无衣，与子同袍~
 */
public class ResourceNotFoundResultRender implements ResultRender {


    private String httpMethod;
    private String httpPath;

    public ResourceNotFoundResultRender(String method, String path) {
        this.httpMethod = method;
        this.httpPath = path;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND,
                "获取不到对应的请求资源：请求路径[" + httpPath + "]" + "请求方法[" + httpMethod + "]");

    }
}
