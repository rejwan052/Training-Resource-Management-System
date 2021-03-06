package com.trms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.core.types.Predicate;
import com.trms.datatables.DatatablesPage;
import com.trms.persistence.model.Department;
import com.trms.service.IDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDepartmentService departmentService;

    // Get all departments
    @GetMapping("/departments")
    public ResponseEntity<Page<Department>> getAllDepartments(@QuerydslPredicate(root = Department.class) Predicate predicate,
                                                            @PageableDefault(size=10) @SortDefault.SortDefaults({
                                                            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)}) Pageable pageable){

        return departmentService.getAllDepartmentsResponse(predicate,pageable);

    }

    // Get departments by searching name
    @GetMapping("/departments/search-by-name")
    public List<Department> searchDepartmentsByName(@RequestParam(value = "searchTerm") String searchTerm){
        return departmentService.searchByDepartmentName(searchTerm);
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

    //Utility
    private String toJson(DatatablesPage<?> dt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        return mapper.writeValueAsString(dt);
    }


}
