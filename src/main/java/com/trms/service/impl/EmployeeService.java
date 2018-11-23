package com.trms.service.impl;

import com.trms.persistence.model.Employee;
import com.trms.service.IEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    @Override
    public ResponseEntity<List<Employee>> getAllEmployeesResponse() {
        return null;
    }

    @Override
    public ResponseEntity<Employee> getSingleEmployeeResponse(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> createNewEmployee(Employee employee, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> patchUpdateEmployee(Long id, Employee employeeUpdates) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> putUpdateEmployee(Long id, Employee employeeUpdates) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> deleteEmployee(Long id) {
        return null;
    }
}
