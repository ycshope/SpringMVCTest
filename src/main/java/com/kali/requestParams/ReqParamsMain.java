package com.kali.requestParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqParamsMain {
    @RequestMapping(value = "/")
    public String reqParamsMain(){
        return "index";
    }
}
