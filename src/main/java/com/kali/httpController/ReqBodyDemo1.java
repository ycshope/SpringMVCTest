package com.kali.httpController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReqBodyDemo1 {
    @PostMapping("/reqBodyDemo1")
    public ModelAndView reqBodyDemo1(@RequestBody String reqBody) {
        System.out.println(reqBody);
        ModelAndView mav = new ModelAndView();
        mav.addObject("reqBody", reqBody);
        mav.setViewName("success");
        return mav;
    }
}
