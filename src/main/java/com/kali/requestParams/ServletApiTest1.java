package com.kali.requestParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ServletApiTest1 {

    @RequestMapping("/servletApiTest1")
    public String servletApiTest1(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username=" + username + "\tpassword=" + password);
        return "servletapitest1";
    }
}
