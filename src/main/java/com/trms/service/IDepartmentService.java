package com.trms.service;

import com.querydsl.core.types.Predicate;
import com.trms.persistence.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IDepartmentService {

    ResponseEntity<Page<Department>> getAllDepartmentsResponse(Predicate predicate, Pageable pageable);
    ResponseEntity<Department> getSingleDepartmentResponse(Long id);
    ResponseEntity<Department> createNewDepartment(Department department, HttpServletRequest request);
    ResponseEntity<Department> patchUpdateDepartment(Long id, Department departmentUpdates);
    ResponseEntity<Department> putUpdateDepartment(Long id, Department departmentUpdates);
    ResponseEntity<Department> deleteDepartment(Long id);
    Page<Department> searchDepartments(String searchTerm,Pageable pageable);
    List<Department> searchByDepartmentName(String searchTerm);

}
