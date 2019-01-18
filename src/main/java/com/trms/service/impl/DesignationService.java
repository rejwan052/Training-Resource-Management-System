package com.trms.service.impl;

import com.querydsl.core.types.Predicate;
import com.trms.exception.EntityAttributeAlreadyExistsException;
import com.trms.exception.ResourceNotFoundException;
import com.trms.persistence.model.Department;
import com.trms.persistence.model.Designation;
import com.trms.persistence.repository.DesignationRepository;
import com.trms.predicates.DepartmentPredicates;
import com.trms.predicates.DesignationPredicates;
import com.trms.service.IDesignationService;
import com.trms.utility.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DesignationService implements IDesignationService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private DesignationRepository designationRepository;

    private ApiUtils apiUtils;

    public DesignationService(DesignationRepository designationRepository,ApiUtils apiUtils) {
        Assert.notNull(designationRepository, "DesignationRepository must not be null!");
        Assert.notNull(apiUtils, "ApiUtils must not be null!");
        this.designationRepository = designationRepository;
        this.apiUtils = apiUtils;
    }

    @Override
    public ResponseEntity<Page<Designation>> getAllDesignationsResponse(Predicate predicate, Pageable pageable) {
        Page<Designation> page = designationRepository.findAll(predicate,pageable);
        return new ResponseEntity<Page<Designation>>(page, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Designation> getSingleDesignationResponse(Long id) {
        Designation getDesignation = findDesignationIfExists(id);
        return new ResponseEntity<Designation>(getDesignation, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Designation> createNewDesignation(Designation designation, HttpServletRequest request) {

        if(isDesignationNameExist(designation.getName())) {
            throw new EntityAttributeAlreadyExistsException("Designation already exists with "+designation.getName());
        }

        Designation newDesignation = designationRepository.saveAndFlush(designation);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", designationUrlHelper(newDesignation, request));

        return new ResponseEntity<Designation>(newDesignation, responseHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Designation> patchUpdateDesignation(Long id, Designation designationUpdates) {
        Designation existingDesignation = findDesignationIfExists(id);
        apiUtils.merge(existingDesignation, designationUpdates);
        existingDesignation.setId(id);
        return new ResponseEntity<Designation>(designationRepository.saveAndFlush(existingDesignation),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Designation> putUpdateDesignation(Long id, Designation designationUpdates) {
        Designation existingDesignation = findDesignationIfExists(id);
        BeanUtils.copyProperties(designationUpdates,existingDesignation);
        existingDesignation.setId(id);
        return new ResponseEntity<Designation>(designationRepository.saveAndFlush(existingDesignation),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Designation> deleteDesignation(Long id) {
        Designation existingDesignation = findDesignationIfExists(id);
        designationRepository.delete(existingDesignation);
        return new ResponseEntity<Designation>(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Designation> searchByDesignationName(String searchTerm) {

        Predicate designationSearchPredicate = DesignationPredicates.searchDesignationsByName(searchTerm);
        LOGGER.info("Department search predicate :"+designationSearchPredicate.toString());
        Iterable<Designation> designationIterable = designationRepository.findAll(designationSearchPredicate);
        List<Designation> designationList = new ArrayList<>();
        designationIterable.forEach(designationList::add);

        return designationList;
    }

    // Non API
    private boolean isDesignationNameExist(final String departmentName) {
        final Designation designation = designationRepository.findByNameIgnoreCase(departmentName);
        if(null != designation) {
            return true;
        }
        return false;
    }

    private String designationUrlHelper(Designation designation, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(designation.getId());

        return resourcePath.toString();
    }

    private Designation findDesignationIfExists(Long id) {
        return designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation", "id", id));
    }
}
