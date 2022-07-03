package com.kali.handle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HanderControllerDemo1 {
    @RequestMapping("/**/handerControllerDemo1")
    public String handerControllerDemo1() {
        return "success";
    }
}
