package com.trms.payload;

import com.trms.enums.Gender;
import com.trms.persistence.model.Address;
import com.trms.persistence.model.Department;
import com.trms.persistence.model.Designation;
import com.trms.validator.Enum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

public class EmployeeCreateRequest implements Serializable {

    private Long id;

    @NotBlank(message="{NotBlank.employee.firstName}")
    private String firstName;

    private String lastName;

    @Email(message="{Email.employee.email}")
    private String email;

    @Enum(enumClass = Gender.class)
    private String gender;

    private LocalDate dateOfBirth;

    private Designation designation;

    private Department department;

    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
