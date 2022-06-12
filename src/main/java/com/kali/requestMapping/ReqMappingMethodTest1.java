package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReqMappingMethodTest1 {

//    @RequestMapping(
//            value = "/reqMappingMethodTest1",
//            //  配置post方法
//            method = {RequestMethod.POST}
//    )
    //也可以用@PostMapping来设置请求方法
    @PostMapping(value = "/reqMappingMethodTest1")
    public String reqMappingMethodTest1() {
        return "methodtest1";
    }
}
