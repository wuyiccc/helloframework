package com.wuyiccc.helloframework.mvc;

import com.wuyiccc.helloframework.aop.AspectWeaver;
import com.wuyiccc.helloframework.core.BeanContainer;
import com.wuyiccc.helloframework.injection.DependencyInjector;
import com.wuyiccc.helloframework.mvc.processor.RequestProcessor;
import com.wuyiccc.helloframework.mvc.processor.impl.ControllerRequestProcessor;
import com.wuyiccc.helloframework.mvc.processor.impl.JspRequestProcessor;
import com.wuyiccc.helloframework.mvc.processor.impl.PreRequestProcessor;
import com.wuyiccc.helloframework.mvc.processor.impl.StaticResourceRequestProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author wuyiccc
 * @date 2020/7/14 21:42
 * 岂曰无衣，与子同袍~
 */
@WebServlet("/*")
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private static final String HELLOFRAMEWORK_CONFIG_FILE = "config/helloframework-config.properties";


    private List<RequestProcessor> PROCESSOR = new ArrayList<>();

    @Override
    public void init() throws ServletException {

        // 1. 初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();

        beanContainer.loadBeans(getHelloFrameworkScanPackages());
        new AspectWeaver().doAop();
        new DependencyInjector().doIoc();
        // 2. 初始化请求处理器责任链
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 创建责任链对象实例
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(), req, resp);
        // 2. 通过责任链模式来依次调用请求处理器对请求进行处理
        requestProcessorChain.doRequestProcessorChain();
        // 3. 对处理结果进行渲染
        requestProcessorChain.doRender();
    }

    private String getHelloFrameworkScanPackages() {
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(HELLOFRAMEWORK_CONFIG_FILE);
        try {
            properties.load(in);
        } catch (IOException e) {
            log.warn("The config/helloframework.properties can not load");
            e.printStackTrace();
        }

        String scanPackages = properties.getProperty("helloframework.scan.packages");
        log.info("this is scanPackages: {}", scanPackages);
        return scanPackages;
    }
}
