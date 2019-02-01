package com.trms.controller;

import com.querydsl.core.types.Predicate;
import com.trms.payload.EmployeeRequest;
import com.trms.persistence.model.Employee;
import com.trms.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    // Get all employees
    @GetMapping("/employees")
    public ResponseEntity<Page<Employee>> getAllEmployees(@QuerydslPredicate(root = Employee.class) Predicate predicate,
            @PageableDefault(size = 10) @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) }) Pageable pageable) {

        return employeeService.getAllEmployeesResponse(predicate, pageable);

    }

    // Get a single employee
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getSingleEmployee(@PathVariable Long id) {
        return employeeService.getSingleEmployeeResponse(id);
    }

    // Create a new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> createNewEmployee(@Valid @RequestBody EmployeeRequest employee,
            HttpServletRequest request) {
        return employeeService.createNewEmployee(employee, request);
    }

    // Update employee with PATCH
    @PatchMapping("/employees/{id}")
    public ResponseEntity<Employee> patchUpdateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.patchUpdateEmployee(id, employee);
    }

    // Update employee with PUT
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> putUpdateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employee) {
        return employeeService.putUpdateEmployee(id, employee);
    }

    // Delete employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/employees/check-email")
    public boolean checkEmailAddress(@RequestParam(value = "email") String email,@RequestParam(value = "employeeId") String employeeId){
        return employeeService.checkEmailNotTaken(email,employeeId);
    }

}
