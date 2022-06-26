package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class SessionTest1 {
    @RequestMapping(value = "/sessionTest1")
    public String sessionTest1(HttpSession session){
        session.setAttribute("testScope","Hello Session");
        return "success";
    }
}
