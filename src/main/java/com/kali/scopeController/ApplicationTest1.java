package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller
public class ApplicationTest1 {
    @RequestMapping("/applicationTest1")
    public String applicationTest1(HttpSession session){
        ServletContext application = session.getServletContext();
        //整个项目都生效
        application.setAttribute("testScope","Hello Application");
        return "success";
    }
}
