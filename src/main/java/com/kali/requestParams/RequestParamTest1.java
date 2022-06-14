package com.kali.requestParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class RequestParamTest1 {
    //  @RequestParam(value = "pwd") 给形参赋别名
    @RequestMapping("/requestParamTest1")
    public String requestParamTest1(
            @RequestParam(defaultValue = "admin") String username,
            @RequestParam(value = "pwd") String password,
            @RequestParam(required = false) String[] hobby) {
        System.out.println("username=" + username + "\tpassword=" + password + "\thobby=" + Arrays.toString(hobby));
        return "controllerparams";
    }
}
