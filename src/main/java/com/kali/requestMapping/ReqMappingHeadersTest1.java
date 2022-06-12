package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqMappingHeadersTest1 {

    //匹配Host
    @RequestMapping(value = "/reqMappingHeadersTest1", headers = "Host=localhost:28080")
    public String reqMappingHeadersTest1() {
        return "headerstest1";
    }
}
