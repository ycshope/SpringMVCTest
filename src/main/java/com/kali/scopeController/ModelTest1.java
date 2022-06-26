package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModelTest1 {
    @RequestMapping("/modeltest1")
    public String modeltest(Model model) {
        model.addAttribute("testScope", "Hello Model");
        return "success";
    }
}
