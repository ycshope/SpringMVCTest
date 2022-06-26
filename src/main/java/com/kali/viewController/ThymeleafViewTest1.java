package com.kali.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafViewTest1 {
    @RequestMapping("/thymeleafViewTest1")
    public String thymeleafViewTest1(){
        return "success";
    }
}
