package com.trms.service.impl;

import com.trms.exception.ResourceNotFoundException;
import com.trms.persistence.model.Address;
import com.trms.persistence.model.Employee;
import com.trms.persistence.repository.AddressRepository;
import com.trms.service.IAddressService;
import com.trms.utility.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AddressService implements IAddressService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private AddressRepository addressRepository;
    private ApiUtils apiUtils;

    public AddressService(AddressRepository addressRepository, ApiUtils apiUtils) {
        this.addressRepository = addressRepository;
        this.apiUtils = apiUtils;
    }

    @Override
    public ResponseEntity<List<Address>> getAllAddressResponseByEmployeeId(Long employeeId) {
        List<Address> addresses = addressRepository.findByEmployeeId(employeeId);
        return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Address> getSingleAddressResponse(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Address>> createNewAddresses(List<Address> addresses) {
        List<Address> newAddresses = addressRepository.saveAll(addresses);
        return new ResponseEntity<List<Address>>(newAddresses, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Address> patchUpdateAddress(Long id, Address addressUpdates) {
        return null;
    }

    @Override
    public ResponseEntity<List<Address>> putUpdateAddresses(Employee employee, List<Address> addressesUpdates,
            List<Long> removedAddressIds) {

        List<Address> updatedAddresses = new ArrayList<Address>();

        // remove protocol detail
        if (!CollectionUtils.isEmpty(removedAddressIds)) {
            List<Address> removedAddresses = addressRepository.deleteByIdIn(removedAddressIds);
            LOGGER.info("removed addresses" + Arrays.toString(removedAddresses.toArray()));
        }

        if (!CollectionUtils.isEmpty(addressesUpdates)) {
            for (Address updateAddress : addressesUpdates) {

                Long addressId = updateAddress.getId();
                Address existingAddress = null;
                Address updatedAddress = null;

                if (null != addressId) {
                    existingAddress = findAddressIfExists(addressId);
                    BeanUtils.copyProperties(updateAddress, existingAddress);
                    // Ensure ID remains unchanged and protocol
                    existingAddress.setId(addressId);
                    existingAddress.setEmployee(employee);
                    updatedAddress = addressRepository.saveAndFlush(existingAddress);
                } else {
                    LOGGER.info("New address found with address :" + updateAddress.getAddressLine1());
                    updateAddress.setEmployee(employee);
                    updatedAddress = addressRepository.saveAndFlush(updateAddress);
                }
                updatedAddresses.add(updatedAddress);
            }
        }
        return new ResponseEntity<List<Address>>(updatedAddresses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Address> deleteAddress(Long id) {
        return null;
    }

    // Non API

    private String addressUrlHelper(Address address, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(address.getId());

        return resourcePath.toString();
    }

    private Address findAddressIfExists(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address ", "id", id));
    }

    private Address findByAddressId(List<Address> addresses, Long addressId) {
        return apiUtils.findByProperty(addresses, address -> addressId == address.getId());
    }

}
