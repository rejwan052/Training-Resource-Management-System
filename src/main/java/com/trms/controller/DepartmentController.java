package com.trms.controller;

import com.querydsl.core.types.Predicate;
import com.trms.persistence.model.Department;
import com.trms.service.IDepartmentService;
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
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    // Get all protocols
    @GetMapping("/departments")
    public ResponseEntity<Page<Department>> getAllProtocols(@QuerydslPredicate(root = Department.class) Predicate predicate,
                                                            @PageableDefault(size=15) @SortDefault.SortDefaults({
                                                            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)}) Pageable pageable){

        return departmentService.getAllDepartmentsResponse(predicate,pageable);

    }

    // Create a new departments
    @PostMapping("/departments")
    public ResponseEntity<Department> createNewProtocol(@Valid @RequestBody Department department, HttpServletRequest request){
        return departmentService.createNewDepartment(department, request);
    }


    // Get a single department


    // Update a department


    // Delete a department


}
