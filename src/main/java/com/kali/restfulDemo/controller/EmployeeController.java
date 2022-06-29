package com.kali.restfulDemo.controller;

import com.kali.restfulDemo.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeController {

    //自动装配,默认根据bytype,否则根据byname
    @Autowired
    private EmployeeDao employeeDao;
}
