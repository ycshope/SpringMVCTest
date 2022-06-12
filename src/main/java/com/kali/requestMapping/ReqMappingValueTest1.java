package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqMappingValueTest1 {

    @RequestMapping(
            value = {"/reqMappingValueTest1", "/reqMappingValueTest2"}
    )
    public String reqMappingValueTest1() {
        return "valuetest1";
    }
}
