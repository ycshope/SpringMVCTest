package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController1 {
    @RequestMapping(value = "/")
    public String testController1() {
        return "index";
    }
}
