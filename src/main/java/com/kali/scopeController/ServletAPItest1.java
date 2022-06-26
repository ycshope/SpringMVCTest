package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ServletAPItest1 {
    @RequestMapping("/servletAPItest1")
    public String servletAPItest1(HttpServletRequest request){
        request.setAttribute("testScope","Hello servletAPIctx");
        return "success";
    }
}
