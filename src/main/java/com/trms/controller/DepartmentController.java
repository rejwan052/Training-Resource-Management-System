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

    // Get a single department
    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getSingleDepartment(@PathVariable Long id){
        return departmentService.getSingleDepartmentResponse(id);
    }

    // Create a new department
    @PostMapping("/departments")
    public ResponseEntity<Department> createNewProtocol(@Valid @RequestBody Department department, HttpServletRequest request){
        return departmentService.createNewDepartment(department, request);
    }

    // Update Department with PATCH
    @PatchMapping("/departments/{id}")
    public ResponseEntity<Department> patchUpdateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.patchUpdateDepartment(id, department);
    }

    // Update Department with PUT
    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> putUpdateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.putUpdateDepartment(id, department);
    }

    // Delete Department
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }


}
