package com.wuyiccc.helloframework.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2020/7/15 8:00
 * 岂曰无衣，与子同袍~
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPathInfo {

    //http请求方法
    private String httpMethod;

    //http请求路径
    private String httpPath;

}
