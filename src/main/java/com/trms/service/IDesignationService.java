package com.trms.service;

import com.querydsl.core.types.Predicate;
import com.trms.payload.Response;
import com.trms.persistence.model.Designation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IDesignationService {

    ResponseEntity<Page<Designation>> getAllDesignationsResponse(Predicate predicate, Pageable pageable);
    ResponseEntity<Designation> getSingleDesignationResponse(Long id);
    ResponseEntity<Designation> createNewDesignation(Designation designation, HttpServletRequest request);
    ResponseEntity<Designation> patchUpdateDesignation(Long id, Designation designationUpdates);
    ResponseEntity<Designation> putUpdateDesignation(Long id, Designation designationUpdates);
    ResponseEntity<Designation> deleteDesignation(Long id);
    List<Designation> searchByDesignationName(String searchTerm);
    Response gridList(HttpServletRequest request);
    long countAllDesignations();
}
