package com.kali.requestParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqHeaderTest1 {
    @RequestMapping("/reqHeaderTest1")
    public String reqHeaderTest1(
            @RequestHeader(value = "hostheader",defaultValue = "null") String host){
        return "reqheaderparam";
    }
}
