package com.kali.scopeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Objects;

@Controller
public class MapTest1 {
    @RequestMapping(value = "/mapTest1")
    public String mapTest1(Map<String, Object> map) {
        map.put("testScope","Hello Map");
        return "success";
    }
}
