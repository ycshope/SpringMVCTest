package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModelAndViewTest1 {
    @RequestMapping(value = "/modelAndViewTest1")
    public ModelAndView modelAndViewTest1() {
        /**
         * ModelAndView有Model和View的功能被
         * Model主要用于请求域共享数据
         * View主要用于返回视图,实现页面跳转
         */
        ModelAndView mav = new ModelAndView();
        //向请求域共享数据
        mav.addObject("testScope", "Hello ModelAndViewCtx");
        //设置视图,实现页面跳转
        mav.setViewName("success");
        return mav;
    }
}
