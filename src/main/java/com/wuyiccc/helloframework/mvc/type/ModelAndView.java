package com.wuyiccc.helloframework.mvc.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/7/15 7:59
 * 岂曰无衣，与子同袍~
 */
public class ModelAndView {


    // 页面所在的路径
    @Getter
    private String view;

    // 页面的data数据
    @Getter
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public ModelAndView addViewData(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }
}
