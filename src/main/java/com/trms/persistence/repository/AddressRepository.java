package com.trms.persistence.repository;

import com.trms.persistence.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByEmployeeId(long employeeId);

    List<Address> deleteByIdIn(List<Long> ids);

}
