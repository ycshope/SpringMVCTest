package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/classReqMapping")
public class ClassReqMappingTest1 {

    //此时请求映射所映射的请求的路径为: /classReqMapping/classtest1
    @RequestMapping("/classtest1")
    public String test1() {
        return "classtest1";
    }
}
