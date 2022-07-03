package com.kali.httpController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxDemo1 {
    @PostMapping("/ajaxDemo1")
    @ResponseBody
    public String ajaxDemo1(String username, String password) {
        System.out.println("username:" + username + "\npassword:" + password);
        return "Hello Ajax";
    }
}
