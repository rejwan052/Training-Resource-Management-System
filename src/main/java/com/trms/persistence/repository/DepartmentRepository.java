package com.trms.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.trms.persistence.model.Department;
import com.trms.persistence.model.QDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>, QuerydslPredicateExecutor<Department>, QuerydslBinderCustomizer<QDepartment> {

    Department findByNameIgnoreCase(String name);

    @Override
    default public void customize(QuerydslBindings bindings, QDepartment root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}
