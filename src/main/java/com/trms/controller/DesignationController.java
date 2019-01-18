package com.trms.controller;

import com.querydsl.core.types.Predicate;
import com.trms.persistence.model.Department;
import com.trms.persistence.model.Designation;
import com.trms.service.IDepartmentService;
import com.trms.service.IDesignationService;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DesignationController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDesignationService designationService;

    // Get all designations
    @GetMapping("/designations")
    public ResponseEntity<Page<Designation>> getAllDesignations(@QuerydslPredicate(root = Designation.class) Predicate predicate, @PageableDefault(size=10) @SortDefault.SortDefaults({
                                                                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)}) Pageable pageable){

        return designationService.getAllDesignationsResponse(predicate,pageable);

    }

    // Get designations by searching name
    @GetMapping("/designations/search-by-name")
    public List<Designation> searchDesignationsByName(@RequestParam(value = "searchTerm") String searchTerm){
        return designationService.searchByDesignationName(searchTerm);
    }

    // Get a single designation
    @GetMapping("/designations/{id}")
    public ResponseEntity<Designation> getSingleDesignation(@PathVariable Long id){
        return designationService.getSingleDesignationResponse(id);
    }

    // Create a new designation
    @PostMapping("/designations")
    public ResponseEntity<Designation> createNewDesignation(@Valid @RequestBody Designation designation, HttpServletRequest request){
        return designationService.createNewDesignation(designation, request);
    }

    // Update designation with PATCH
    @PatchMapping("/designations/{id}")
    public ResponseEntity<Designation> patchUpdateDesignation(@PathVariable Long id, @RequestBody Designation designation) {
        return designationService.patchUpdateDesignation(id, designation);
    }

    // Update designation with PUT
    @PutMapping("/designations/{id}")
    public ResponseEntity<Designation> putUpdateDesignation(@PathVariable Long id, @RequestBody Designation designation) {
        return designationService.putUpdateDesignation(id, designation);
    }

    // Delete designation
    @DeleteMapping("/designations/{id}")
    public ResponseEntity<Designation> deleteDesignation(@PathVariable Long id) {
        return designationService.deleteDesignation(id);
    }
}
