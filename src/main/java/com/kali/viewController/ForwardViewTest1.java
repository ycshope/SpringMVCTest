package com.kali.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardViewTest1 {
    @RequestMapping("/forwardViewTest1")
    public String forwardViewTest1(){
        //转发给thymeleafViewTest1
        return "forward:/thymeleafViewTest1";
    }
}
