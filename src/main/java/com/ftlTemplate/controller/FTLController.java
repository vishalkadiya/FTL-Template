package com.ftlTemplate.controller;

import com.ftlTemplate.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee/data")
public class FTLController {

    @Autowired
    EmployeeService employeeService;
    @GetMapping("/welcome")
    public ResponseEntity<String> findById(@RequestParam String name) {
        return employeeService.findByName(name);
    }
}
