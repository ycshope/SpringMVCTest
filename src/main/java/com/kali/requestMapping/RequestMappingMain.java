package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestMappingMain {

    @RequestMapping(value = "/")
    public String classReqMappingDemo1(){
        return "index";
    }
}
