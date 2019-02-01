package com.trms.service;

import com.trms.persistence.model.Address;
import com.trms.persistence.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAddressService {

    ResponseEntity<List<Address>> getAllAddressResponseByEmployeeId(Long employeeId);

    ResponseEntity<Address> getSingleAddressResponse(Long id);

    ResponseEntity<List<Address>> createNewAddresses(List<Address> addresses);

    ResponseEntity<Address> patchUpdateAddress(Long id, Address addressUpdates);

    ResponseEntity<List<Address>> putUpdateAddresses(Employee employee, List<Address> addressesUpdates,
            List<Long> removedAddressIds);

    ResponseEntity<Address> deleteAddress(Long id);

}
