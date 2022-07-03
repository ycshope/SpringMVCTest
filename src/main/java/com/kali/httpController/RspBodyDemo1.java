package com.kali.httpController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RspBodyDemo1 {
    //添加@ResponseBody后不再先向View做解析后返回给前端,而是直接输出内容到前端
    @GetMapping("/rspBodyDemo1")
    @ResponseBody
    public String rspBodyDemo1(){
        return "Hello rspBodyDemo1!</br><a href='/SpringMVCTest'>Index</a>";
    }
}
