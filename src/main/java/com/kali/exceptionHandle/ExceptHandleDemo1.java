package com.kali.exceptionHandle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptHandleDemo1 {

    @RequestMapping(value = "/exceptHandleDemo1")
    public String exceptHandleDemo1() {
        System.out.println(1 / 0);
        //异常触发时直接找到@ExceptionHandler(ArithmeticException.class)
        return "success";
    }
}
