package com.trms.utility;

import com.trms.pagination.PaginationCriteria;

import java.util.Collection;
import java.util.function.Predicate;

public interface ApiUtils {

    <T> T findByProperty(Collection<T> col, Predicate<T> filter);

    void merge(Object obj, Object update);

    boolean isObjectEmpty(Object object);

    boolean isCollectionEmpty(Collection<?> collection);

    String buildPaginatedQuery(String baseQuery, PaginationCriteria paginationCriteria);
}
