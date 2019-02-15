package com.trms.service.impl;

import com.querydsl.core.types.Predicate;
import com.trms.enums.Gender;
import com.trms.exception.ResourceNotFoundException;
import com.trms.payload.EmployeeRequest;
import com.trms.persistence.model.Address;
import com.trms.persistence.model.Employee;
import com.trms.persistence.repository.DepartmentRepository;
import com.trms.persistence.repository.DesignationRepository;
import com.trms.persistence.repository.EmployeeRepository;
import com.trms.service.IAddressService;
import com.trms.service.IEmployeeService;
import com.trms.utility.ApiUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    private EmployeeRepository employeeRepository;

    private ApiUtils apiUtils;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DepartmentRepository departmentRepositry;

    @Autowired
    private IAddressService addressService;

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
    public ResponseEntity<Employee> createNewEmployee(EmployeeRequest employeeRequest, HttpServletRequest request) {

        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setFullName();
        employee.setEmail(employeeRequest.getEmail());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        if(employeeRequest.getGender().toUpperCase().equalsIgnoreCase("MALE")){
            employee.setGender(Gender.MALE);
        }else{
            employee.setGender(Gender.FEMALE);
        }

        employee.setDepartment(employeeRequest.getDepartment());
        employee.setDesignation(employeeRequest.getDesignation());


        /*Optional<Designation> designation = designationRepository.findById(employeeCreateRequest.getDesignationId());
        employee.setDesignation(designation.get());

        Optional<Department> department = departmentRepositry.findById(employeeCreateRequest.getDepartmentId());
        employee.setDepartment(department.get());*/

        Employee newEmployee = employeeRepository.saveAndFlush(employee);

        /*Create Employee Address*/
        if(null != employeeRequest.getAddress()){
            createEmployeeAddress(newEmployee,employeeRequest.getAddress());
        }

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
    public ResponseEntity<Employee> putUpdateEmployee(Long id, EmployeeRequest employeeUpdates) {

        Employee existingEmployee = findEmployeeIfExists(id);

        BeanUtils.copyProperties(employeeUpdates,existingEmployee);

        existingEmployee.setFullName();
        existingEmployee.setId(id);

        Employee updatedEmployee = employeeRepository.saveAndFlush(existingEmployee);

        /*Create Employee Address*/
        if(null != employeeUpdates.getAddress()){
            createEmployeeAddress(updatedEmployee,employeeUpdates.getAddress());
        }

        return new ResponseEntity<Employee>(updatedEmployee,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> deleteEmployee(Long id) {
        Employee existingEmployee = findEmployeeIfExists(id);
        employeeRepository.delete(existingEmployee);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

    @Override
    public boolean checkEmailNotTaken(String emailAddress,String employeeId) {

        if(null == employeeId){
            Optional<Employee> employee = employeeRepository.findByEmail(emailAddress);
            if(employee.isPresent()){
                return true;
            }
        }else{
            Optional<Employee> employee = employeeRepository.findByEmailAndIdNot(emailAddress,Long.valueOf(employeeId));
            if(employee.isPresent()){
                return true;
            }
        }

        return false;
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

    private void createEmployeeAddress(Employee employee,Address address){
        if(null != address){
            address.setEmployee(employee);
            addressService.createNewAddresses(Arrays.asList(address));
        }
    }
}
