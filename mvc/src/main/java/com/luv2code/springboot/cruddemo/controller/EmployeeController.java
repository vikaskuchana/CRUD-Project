package com.luv2code.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@Controller
@RequestMapping("/employees")
public class EmployeeController {

  private EmployeeService empSvc;
  public EmployeeController(EmployeeService theEmployeeService){
    this.empSvc=theEmployeeService;
  }

  @GetMapping("/list")
  public String getMethodName(Model model) {
    
    List<Employee> allEmployees=empSvc.findAll();

    model.addAttribute("employees", allEmployees); // employees is the key we use in thymeleaf
    //Model connects the dbData to thymeleaf.

    return "employees/list-employees";
  }
  
  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model model) {

    //create model attribute to bind from data 
    Employee theEmployee=new Employee();
    model.addAttribute("employee", theEmployee);

    return "employees/employee-form";
  }

  @PostMapping("/save")
  public String saveEmployee(@ModelAttribute("employees") Employee employee) {
      //Create model attribute to bind from data   
      // Employee employee=(Employee) model.getAttribute("employee");

      //saving the object.
      empSvc.save(employee);

      //redirect to avoid duplicate submissions
      return "redirect:/employees/list";
  }
  
  @GetMapping("/showFormForUpdate")
  public String updateEmployee(@RequestParam("employeeId") int employeeId , Model model) {
      //get emp by id
      Employee employee=empSvc.findById(employeeId);

      //bund it to the model
      model.addAttribute("employee", employee);

      return "/employees/employee-form";
  }

  @GetMapping("/showFormForDelete")
  public String deleteEmployee(@RequestParam("employeeId") int employeeId, Model model) {
    empSvc.deleteById(employeeId);
    return "redirect:/employees/list";
  }
  
  
}
