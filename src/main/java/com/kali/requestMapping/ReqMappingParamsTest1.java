package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqMappingParamsTest1 {
    @RequestMapping(
            value = "/reqMappingParamsTest1",
            //params参数必须全部符合才能访问
            params = {"username", "password=123456"}
    )
    public String reqMappingParamsTest1() {
        return "paramstest1";
    }
}
