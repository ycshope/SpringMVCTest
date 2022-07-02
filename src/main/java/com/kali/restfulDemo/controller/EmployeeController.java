package com.kali.restfulDemo.controller;

import com.kali.restfulDemo.bean.Employee;
import com.kali.restfulDemo.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {

    //自动装配,默认根据bytype,否则根据byname
    @Autowired
    private EmployeeDao employeeDao;

    //域对象共享数据:model
    @GetMapping(value = "/employee")
    public String getEmpList(Model model) {
        Collection<Employee> empList = employeeDao.getAll();
        model.addAttribute("empList", empList);
        return "employee_list";
    }

    @DeleteMapping(value = "/employee/{id}")
    public String delEmp(@PathVariable Integer id){
        employeeDao.delete(id);
        return "redirect:/employee";
    }
}
