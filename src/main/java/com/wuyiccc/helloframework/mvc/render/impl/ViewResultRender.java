package com.wuyiccc.helloframework.mvc.render.impl;

import com.wuyiccc.helloframework.mvc.RequestProcessorChain;
import com.wuyiccc.helloframework.mvc.render.ResultRender;
import com.wuyiccc.helloframework.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/7/15 7:55
 * 岂曰无衣，与子同袍~
 */
public class ViewResultRender implements ResultRender {


    public static final String VIEW_PATH = "/templates/";

    private ModelAndView modelAndView;

    /**
     * 对传入的参数进行处理，并赋值给ModelAndView成员变量
     *
     * @param mv
     */
    public ViewResultRender(Object mv) {

        if (mv instanceof ModelAndView) {

            //1.如果入参类型是ModelAndView，则直接赋值给成员变量
            this.modelAndView = (ModelAndView) mv;

        } else if (mv instanceof String) {

            //2.如果入参类型是String，则为视图，需要包装后才赋值给成员变量
            this.modelAndView = new ModelAndView().setView((String) mv);

        } else {

            //3.针对其他情况，则直接抛出异常
            throw new RuntimeException("illegal request result type");

        }
    }


    /**
     * 将请求处理结果按照视图路径转发至对应视图进行展示
     *
     * @param requestProcessorChain
     * @throws Exception
     */
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {

        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();

        String path = modelAndView.getView();

        Map<String, Object> model = modelAndView.getModel();

        for (Map.Entry<String, Object> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        //JSP
        request.getRequestDispatcher(VIEW_PATH + path).forward(request, response);

    }
}
