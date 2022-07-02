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
    //默认是选中和自己相同的参数
    public String delEmp(@PathVariable Integer id) {
        employeeDao.delete(id);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/employee", method = {RequestMethod.POST, RequestMethod.PUT})
    public String addEmp(Employee emp) {
        employeeDao.save(emp);
        return "redirect:/employee";
    }

    // 拿到的对象需要传递到共享域中(/employee_update)
    @GetMapping(value = "/employee/{id}")
    public String getEmp(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        //为什么delete和save不需要?
        model.addAttribute("emp", employee);
        return "employee_update";
    }

    @PutMapping(value = "/employee/{id}")
    public String updateEmp(@PathVariable("id") Integer id, Employee emp) {
        Employee employee = employeeDao.get(id);
        if (employee.getId().equals(id)){
            employeeDao.save(emp);
        }
        return "redirect:/employee";
    }

}
