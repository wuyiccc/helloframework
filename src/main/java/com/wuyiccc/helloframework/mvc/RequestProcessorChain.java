package com.wuyiccc.helloframework.mvc;

import com.wuyiccc.helloframework.mvc.processor.RequestProcessor;
import com.wuyiccc.helloframework.mvc.render.ResultRender;
import com.wuyiccc.helloframework.mvc.render.impl.DefaultResultRender;
import com.wuyiccc.helloframework.mvc.render.impl.InternalErrorResultRender;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * @author wuyiccc
 * @date 2020/7/14 21:42
 * 岂曰无衣，与子同袍~
 */
@Data
@Slf4j
public class RequestProcessorChain {

    // 请求处理器迭代器
    private Iterator<RequestProcessor> requestProcessorIterator;

    private HttpServletRequest request;

    private HttpServletResponse response;

    // http请求方法
    private String requestMethod;

    // http请求路径
    private String requestPath;

    // http响应状态码
    private int responseCode;

    // 请求结果渲染器
    private ResultRender resultRender;


    public RequestProcessorChain(Iterator<RequestProcessor> requestProcessorIterator, HttpServletRequest request, HttpServletResponse response) {
        this.requestProcessorIterator = requestProcessorIterator;
        this.request = request;
        this.response = response;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;
    }

    /**
     * 以责任链的模式执行请求链
     */
    public void doRequestProcessorChain() {
        // 1. 通过迭代器遍历注册的请求处理器实现类列表
        try {
            while (requestProcessorIterator.hasNext()) {
                // 2. 知道某个请求处理器执行后返回为false为止
                if (!requestProcessorIterator.next().process(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            // 3. 期间如果出现异常，则交给内部异常渲染器处理
            this.resultRender = new InternalErrorResultRender(e.getMessage());
            log.error("doRequestProcessorChain error: ", e);
        }

    }


    /**
     * 执行处理器
     */
    public void doRender() {
        // 1. 如果请求处理器实现类均未选择合适的渲染器，则使用默认的
        if (this.resultRender == null) {
            this.resultRender = new DefaultResultRender();
        }
        // 2. 调用渲染器的render方法对结果进行渲染
        try {
            this.resultRender.render(this);
        } catch (Exception e) {
            log.error("doRender error: ", e);
            throw new RuntimeException(e);
        }

    }
}