package com.trms.service.impl;

import com.querydsl.core.types.Predicate;
import com.trms.exception.EntityAttributeAlreadyExistsException;
import com.trms.exception.ResourceNotFoundException;
import com.trms.persistence.model.Department;
import com.trms.persistence.repository.DepartmentRepository;
import com.trms.service.IDepartmentService;
import com.trms.utility.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class DepartmentService implements IDepartmentService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private DepartmentRepository departmentRepository;
    private ApiUtils apiUtils;

    public DepartmentService(DepartmentRepository departmentRepository,ApiUtils apiUtils) {
        this.departmentRepository = departmentRepository;
        this.apiUtils = apiUtils;
    }

    @Override
    public ResponseEntity<Page<Department>> getAllDepartmentsResponse(Predicate predicate, Pageable pageable) {
        Page<Department> page = departmentRepository.findAll(predicate,pageable);
        return new ResponseEntity<Page<Department>>(page, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Department> getSingleDepartmentResponse(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Department> createNewDepartment(Department department, HttpServletRequest request) {

        if(isDepartmentNameExist(department.getName())) {
            throw new EntityAttributeAlreadyExistsException("Department already exists with "+department.getName());
        }

        Department newDepartment = departmentRepository.saveAndFlush(department);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", departmentUrlHelper(newDepartment, request));

        return new ResponseEntity<Department>(newDepartment, responseHeaders, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Department> patchUpdateDepartment(Long id, Department departmentUpdates) {
        return null;
    }

    @Override
    public ResponseEntity<Department> putUpdateDepartment(Long id, Department departmentUpdates) {
        return null;
    }

    @Override
    public ResponseEntity<Department> deleteDepartment(Long id) {
        return null;
    }


    // Non API
    private boolean isDepartmentNameExist(final String departmentName) {
        final Department department = departmentRepository.findByNameIgnoreCase(departmentName);
        if(null != department) {
            return true;
        }
        return false;
    }

    private String departmentUrlHelper(Department department, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(department.getId());

        return resourcePath.toString();
    }

    private Department findDepartmentIfExists(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
    }


}
