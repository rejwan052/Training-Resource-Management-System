package com.trms.predicates;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.trms.persistence.model.QDepartment;

public class DepartmentPredicates {

    private DepartmentPredicates(){}

    public static Predicate searchDepartmentsByNameOrDescription(String searchTerm) {

        BooleanBuilder b = new BooleanBuilder();
        QDepartment qDepartment = QDepartment.department;

        if (searchTerm == null || searchTerm.isEmpty()) {
            return b;
        } else {
            final String[] parts = searchTerm.split("\\s+");

            BooleanBuilder nameBooleanBuilder = new BooleanBuilder();
            if (parts.length >= 2) {
                for (String string : parts) {
                    nameBooleanBuilder = nameBooleanBuilder.or(qDepartment.name.containsIgnoreCase(string));
                }
            } else {
                nameBooleanBuilder = nameBooleanBuilder.or(qDepartment.name.containsIgnoreCase(searchTerm));
            }

            BooleanBuilder descriptionBooleanBuilder = new BooleanBuilder();
            if (parts.length >= 2) {
                for (String string : parts) {
                    descriptionBooleanBuilder = descriptionBooleanBuilder.or(qDepartment.description.containsIgnoreCase(string));
                }
            } else {
                descriptionBooleanBuilder = descriptionBooleanBuilder.or(qDepartment.description.containsIgnoreCase(searchTerm));
            }

            return b.and(nameBooleanBuilder).or(descriptionBooleanBuilder);

        }

    }
}
