package com.kali.controllerDemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//之前用的修饰符是@Component;不过目前是@Controller
@Controller
public class HelloController {
    //"/"-->/WEB-INF/templates/index.html

    //请求映射器,也就是路由
    @RequestMapping(value = "/")
    private String index() {
        //返回视图名称
        //结果返回给视图解析器 springMVC.xml,加上前缀("/WEB-INF/templates/")和 后缀(".html")
        return "index";
    }

    @RequestMapping("/hello")
    private String hello() {
        //被解析器解析后转发到    $prefix+hello+$suffix
        return "hello";
    }
}
