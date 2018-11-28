package com.trms.service.impl;

import com.querydsl.core.types.Predicate;
import com.trms.exception.ResourceNotFoundException;
import com.trms.persistence.model.Designation;
import com.trms.persistence.model.Employee;
import com.trms.persistence.repository.EmployeeRepository;
import com.trms.service.IEmployeeService;
import com.trms.utility.ApiUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    private EmployeeRepository employeeRepository;

    private ApiUtils apiUtils;

    public EmployeeService(EmployeeRepository employeeRepository, ApiUtils apiUtils) {
        this.employeeRepository = employeeRepository;
        this.apiUtils = apiUtils;
    }

    @Override
    public ResponseEntity<Page<Employee>> getAllEmployeesResponse(Predicate predicate, Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(predicate,pageable);
        return new ResponseEntity<Page<Employee>>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getSingleEmployeeResponse(Long id) {
        Employee employee = findEmployeeIfExists(id);
        return new ResponseEntity<Employee>(employee,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createNewEmployee(Employee employee, HttpServletRequest request) {

        Employee newEmployee = employeeRepository.saveAndFlush(employee);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", employeeUrlHelper(newEmployee, request));

        return new ResponseEntity<Employee>(newEmployee, responseHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Employee> patchUpdateEmployee(Long id, Employee employeeUpdates) {
        Employee existingEmployee = findEmployeeIfExists(id);
        apiUtils.merge(existingEmployee, employeeUpdates);
        existingEmployee.setId(id);
        return new ResponseEntity<Employee>(employeeRepository.saveAndFlush(existingEmployee),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> putUpdateEmployee(Long id, Employee employeeUpdates) {
        Employee existingEmployee = findEmployeeIfExists(id);
        BeanUtils.copyProperties(employeeUpdates,existingEmployee);
        existingEmployee.setId(id);
        return new ResponseEntity<Employee>(employeeRepository.saveAndFlush(existingEmployee),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> deleteEmployee(Long id) {
        Employee existingEmployee = findEmployeeIfExists(id);
        employeeRepository.delete(existingEmployee);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }


    // Non API

    private boolean isEmployeeFirstNameAndLastNameExist(final String firstName,final String lastName) {
        final Employee employee = employeeRepository.findByFirstNameAndLastNameIgnoreCase(firstName,lastName);
        if(null != employee) {
            return true;
        }
        return false;
    }

    private String employeeUrlHelper(Employee employee, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(employee.getId());

        return resourcePath.toString();
    }

    private Employee findEmployeeIfExists(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
    }
}
