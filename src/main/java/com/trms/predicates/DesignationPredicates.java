package com.trms.predicates;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.trms.persistence.model.QDesignation;

public class DesignationPredicates {

    private DesignationPredicates(){}

    public static Predicate searchDesignationsByName(String searchTerm) {

        BooleanBuilder b = new BooleanBuilder();
        QDesignation qDesignation = QDesignation.designation;

        if (searchTerm == null || searchTerm.isEmpty()) {
            return b;
        } else {
            final String[] parts = searchTerm.split("\\s+");

            BooleanBuilder nameBooleanBuilder = new BooleanBuilder();
            if (parts.length >= 2) {
                for (String string : parts) {
                    nameBooleanBuilder = nameBooleanBuilder.or(qDesignation.name.containsIgnoreCase(string));
                }
            } else {
                nameBooleanBuilder = nameBooleanBuilder.or(qDesignation.name.containsIgnoreCase(searchTerm));
            }

            return b.and(nameBooleanBuilder);
        }

    }

}
