package com.kali.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectViewTest1 {
    @RequestMapping(value = "/redirectViewTest1")
    public String redirectViewTest1(){
        return "redirect:/thymeleafViewTest1";
    }
}
