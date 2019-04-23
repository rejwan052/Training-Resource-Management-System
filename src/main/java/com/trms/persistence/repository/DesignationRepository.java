package com.trms.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.trms.persistence.model.Designation;
import com.trms.persistence.model.QDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation,Long>, QuerydslPredicateExecutor<Designation>, QuerydslBinderCustomizer<QDesignation>  {

    Designation findByNameIgnoreCase(String name);

    long count();

    @Override
    default public void customize(QuerydslBindings bindings, QDesignation root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
