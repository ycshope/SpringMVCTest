package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqMappingAntTest1 {

    // **表示任意的多级目录
    @RequestMapping(value = "/**/reqMappingAntTest1")
    public String reqMappingAntTest1() {
        return "anttest1";
    }

    //*:表示任意的0个或多个字符
    @RequestMapping(value = "/singleStar*reqMappingAntTest2")
    public String reqMappingAntTest2() {
        return "anttest1";
    }

    //？:表示任意的单个字符
    @RequestMapping(value = "/query?reqMappingAntTest3")
    public String reqMappingAntTest3() {
        return "anttest1";
    }
}
