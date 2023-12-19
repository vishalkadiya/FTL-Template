package com.ftlTemplate.service;

import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    public ResponseEntity<String> findByName(String name);

}
