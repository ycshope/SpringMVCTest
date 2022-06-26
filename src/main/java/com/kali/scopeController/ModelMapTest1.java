package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModelMapTest1 {
    @RequestMapping("/modelMapTest1")
    public String modelMapTest1(ModelMap modelMap){
        modelMap.addAttribute("testScope","Hello ModelMap");
        return "success";
    }
}
