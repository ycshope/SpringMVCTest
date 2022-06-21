package com.kali.requestParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ReqParamsMain {
    @RequestMapping(value = "/")
    public String reqParamsMain(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("JSESSIONID","127.0.0.1");
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            System.out.println("setcookie error:" + e);
        }
        return "index";
    }
}
