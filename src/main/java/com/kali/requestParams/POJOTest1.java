package com.kali.requestParams;

import com.kali.requestParams.POJOObj.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class POJOTest1 {
    @RequestMapping("/pojotest1")
    public String pOJOTest1(User user) {
        System.out.println(user);
        return "pojo";
        //注意User对象需要设置有参和无参构造,以及set和get方法(bean的相关知识)
        //User{username='admin', password='admin', sex='null', age=1}
    }
}
