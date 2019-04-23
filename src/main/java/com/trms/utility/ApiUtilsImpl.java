package com.trms.utility;

import com.trms.pagination.PaginationCriteria;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.Predicate;

@Service
public class ApiUtilsImpl implements ApiUtils {

    @Override
    public <T> T findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).findFirst().orElse(null);
    }

    @Override
    public void merge(Object obj, Object update) {
        if(!obj.getClass().isAssignableFrom(update.getClass())){
            return;
        }

        Method[] methods = obj.getClass().getMethods();

        for(Method fromMethod: methods){
            if(fromMethod.getDeclaringClass().equals(obj.getClass())
                    && fromMethod.getName().startsWith("get")){

                String fromName = fromMethod.getName();
                String toName = fromName.replace("get", "set");

                try {
                    Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
                    Object value = fromMethod.invoke(update, (Object[])null);
                    if(value != null){
                        toMetod.invoke(obj, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;

    }


    @Override
    public boolean isObjectEmpty(Object object) {
        if(object == null) return true;
        else if(object instanceof String) {
            if (((String)object).trim().length() == 0) {
                return true;
            }
        } else if(object instanceof Collection) {
            return isCollectionEmpty((Collection<?>)object);
        }
        return false;
    }


    @Override
    public String buildPaginatedQuery(String baseQuery, PaginationCriteria paginationCriteria) {
        //String queryTemplate = "SELECT FILTERED_ORDERD_RESULTS.* FROM (SELECT BASEINFO.* FROM ( %s ) BASEINFO %s  %s ) FILTERED_ORDERD_RESULTS LIMIT %d, %d";
        StringBuilder sb = new StringBuilder("SELECT FILTERED_ORDERD_RESULTS.* FROM (SELECT BASEINFO.* FROM ( #BASE_QUERY# ) BASEINFO #WHERE_CLAUSE#  #ORDER_CLASUE# ) FILTERED_ORDERD_RESULTS LIMIT #PAGE_NUMBER#, #PAGE_SIZE#");
        String finalQuery = null;
        if(!isObjectEmpty(paginationCriteria)) {
            finalQuery = sb.toString().replaceAll("#BASE_QUERY#", baseQuery)
                    .replaceAll("#WHERE_CLAUSE#", ((isObjectEmpty(paginationCriteria.getFilterByClause())) ? "" : " WHERE ") + paginationCriteria.getFilterByClause())
                    .replaceAll("#ORDER_CLASUE#", paginationCriteria.getOrderByClause())
                    .replaceAll("#PAGE_NUMBER#", paginationCriteria.getPageNumber().toString())
                    .replaceAll("#PAGE_SIZE#", paginationCriteria.getPageSize().toString());
        }
        return (null == finalQuery) ?  baseQuery : finalQuery;
    }


}
