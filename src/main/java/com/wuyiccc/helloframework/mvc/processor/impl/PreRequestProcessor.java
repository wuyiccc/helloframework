package com.wuyiccc.helloframework.mvc.processor.impl;

import com.wuyiccc.helloframework.mvc.RequestProcessorChain;
import com.wuyiccc.helloframework.mvc.processor.RequestProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyiccc
 * @date 2020/7/14 22:40
 * 岂曰无衣，与子同袍~
 */
@Slf4j
public class PreRequestProcessor implements RequestProcessor {

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {

        // 1.设置请求编码，将其统一设置成UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding("UTF-8");
        // 2.将请求路径末尾的/剔除，为后续匹配Controller请求路径做准备
        // （一般Controller的处理路径是/aaa/bbb，所以如果传入的路径结尾是/aaa/bbb/，
        // 就需要处理成/aaa/bbb）
        String requestPath = requestProcessorChain.getRequestPath();
        //http://localhost:8080/myframework requestPath="/"
        if (requestPath.length() > 1 && requestPath.endsWith("/")) {
            requestProcessorChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        log.info("preprocess request {} {}", requestProcessorChain.getRequestMethod(), requestProcessorChain.getRequestPath());

        return true;
    }
}
