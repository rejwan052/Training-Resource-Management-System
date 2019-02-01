package com.trms.controller;

import com.trms.persistence.model.Address;
import com.trms.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    // Get a employee address
    @GetMapping("/employees/{employeeId}/address")
    public ResponseEntity<List<Address>> getSingleEmployee(@PathVariable Long employeeId) {
        return addressService.getAllAddressResponseByEmployeeId(employeeId);
    }

}