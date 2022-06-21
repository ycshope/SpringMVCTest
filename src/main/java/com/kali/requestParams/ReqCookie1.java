package com.kali.requestParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqCookie1 {
    @RequestMapping("/reqCookie1")
    public String reqCookie1(@CookieValue("JSESSIONID") String JSESSIONID){
        System.out.println("JSESSIONID:"+JSESSIONID);
        return "cookie";
    }
}
