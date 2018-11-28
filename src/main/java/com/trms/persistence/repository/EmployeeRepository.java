package com.trms.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.trms.persistence.model.Employee;
import com.trms.persistence.model.QEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, QuerydslPredicateExecutor<Employee>, QuerydslBinderCustomizer<QEmployee> {

    Employee findByFirstNameAndLastNameIgnoreCase(String firstName,String lastName);

    @Override
    default public void customize(QuerydslBindings bindings, QEmployee qEmployee){
        bindings.bind(String.class).first((StringPath path,String value) -> path.containsIgnoreCase(value));
    };
}
