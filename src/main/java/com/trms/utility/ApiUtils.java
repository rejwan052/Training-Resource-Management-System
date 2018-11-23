package com.trms.utility;

import java.util.Collection;
import java.util.function.Predicate;

public interface ApiUtils {

    <T> T findByProperty(Collection<T> col, Predicate<T> filter);

    void merge(Object obj, Object update);


}
