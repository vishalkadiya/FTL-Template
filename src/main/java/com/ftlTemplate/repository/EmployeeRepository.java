package com.ftlTemplate.repository;

import com.ftlTemplate.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Optional<Employee> findByFirstName(String name);

}
