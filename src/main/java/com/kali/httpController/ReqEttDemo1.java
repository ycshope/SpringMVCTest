package com.kali.httpController;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReqEttDemo1 {
    @PostMapping(value = "/reqEttDemo1")
    public ModelAndView reqEttDemo1(RequestEntity<String> reqEtt) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("reqBody", reqEtt.getBody());
        mav.addObject("reqHeader", reqEtt.getHeaders());
        mav.setViewName("success");
        return mav;
    }
}
