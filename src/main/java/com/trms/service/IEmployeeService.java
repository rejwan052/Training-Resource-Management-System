package com.trms.service;

import com.querydsl.core.types.Predicate;
import com.trms.payload.EmployeeRequest;
import com.trms.persistence.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface IEmployeeService {

    ResponseEntity<Page<Employee>> getAllEmployeesResponse(Predicate predicate, Pageable pageable);
    ResponseEntity<Employee> getSingleEmployeeResponse(Long id);
    ResponseEntity<Employee> createNewEmployee(EmployeeRequest employee, HttpServletRequest request);
    ResponseEntity<Employee> patchUpdateEmployee(Long id, Employee employeeUpdates);
    ResponseEntity<Employee> putUpdateEmployee(Long id, EmployeeRequest employeeUpdates);
    ResponseEntity<Employee> deleteEmployee(Long id);
    boolean checkEmailNotTaken(String emailAddress,String employeeId);
}
