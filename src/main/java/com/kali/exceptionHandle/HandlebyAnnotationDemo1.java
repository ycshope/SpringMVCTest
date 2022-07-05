package com.kali.exceptionHandle;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice将当前类标识为异常处理的组件
@ControllerAdvice
public class HandlebyAnnotationDemo1 {
    //@ExceptionHandler用于设置所标识方法处理的异常，这里仅处理ArithmeticException触发的异常
    @ExceptionHandler(ArithmeticException.class)
    //ex标识当前请求处理中所出现的异常
    public String HandlebyAnnotation(Exception ex, Model model) {
        model.addAttribute("ex", ex);
        return "error";
    }
}
