package com.kali.requestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReqMappingRestParamTest1 {
    //RESTFUL传参,占位符
    @RequestMapping(value = "/reqMappingRestParamTest1/{username}/{password}")
    public String reqMappingRestParamTest1(@PathVariable("username") String username, @PathVariable("password") String password) {
        System.out.println("username=" + username + ";password=" + password);
        return "restfulparamstest1";

    }
}
