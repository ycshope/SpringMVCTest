package com.kali.requestParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
public class ControllerParams1 {
    @RequestMapping(value = "/controllerParams1")
    public String controllerParams1(String username, String password, String[] hobby) {
        System.out.println("username=" + username + "\tpassword=" + password + "\thobby=" + Arrays.toString(hobby));
        return "controllerparams";
    }

}
