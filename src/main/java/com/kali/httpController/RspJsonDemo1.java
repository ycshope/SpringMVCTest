package com.kali.httpController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RspJsonDemo1 {
    @GetMapping("/rspJsonDemo1")
    @ResponseBody
    public User rspJsonDemo1(){
        return new User(1,"Hello Json");
    }
}