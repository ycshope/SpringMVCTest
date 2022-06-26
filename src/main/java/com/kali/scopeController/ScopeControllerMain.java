package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScopeControllerMain {

    @RequestMapping(value = "/")
    public String sharedObjectMain(){
        return "index";
    }
}
