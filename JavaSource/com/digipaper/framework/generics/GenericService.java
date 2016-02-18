package com.digipaper.framework.generics;

import com.digipaper.framework.enums.ClauseType;
import com.digipaper.framework.query.QueryParam;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class GenericService<T extends GenericModel> {

    @Inject
    EntityManager entityManager;

    protected Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericService(){
        type = (Class<T>) getTypeArguments(GenericService.class, getClass()).get(0);
    }

    public static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else {
            return null;
        }
    }

    public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                type = ((Class<?>) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class<?>) parameterizedType.getRawType();

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class<?>) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<>();

        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

    public void Save(T model) throws Exception {
        entityManager.joinTransaction();

        if(model.getId() == null) {
            entityManager.persist(model);
        } else {
            entityManager.merge(model);
        }
    }

    public void Delete(T model) throws Exception {
        entityManager.joinTransaction();

        if(model.getId() != null) {
            entityManager.remove(entityManager.merge(model));
        }
    }

    public List<T> GetAll(String order) throws Exception {
        entityManager.joinTransaction();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> queryRoot = criteriaQuery.from(type);

        if (order != null && !"".equals(order)){
            String[] orderParams = order.split(",");
            for (String o : orderParams)
                criteriaQuery.orderBy(criteriaBuilder.asc(queryRoot.get(o)));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<T> GetAutoCompleteData(List<QueryParam> where, String order, String restriction, Integer limit) {
        entityManager.joinTransaction();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> queryRoot = criteriaQuery.from(type);

        List<Predicate> predicateList = new LinkedList<>();

        if (where != null) {
            for (QueryParam queryParam : where) {
                String key = queryParam.getKey();
                Object value = queryParam.getValue();
                ClauseType clauseType = queryParam.getType();

                if (ClauseType.EQUAL.equals(clauseType) || ClauseType.EQUALFORCODE.equals(clauseType)) {
                    if (value instanceof GenericModel) {
                        value = ((GenericModel) value).getId();
                    }
                    predicateList.add(criteriaBuilder.equal(queryRoot.get(key), value));
                } else if (ClauseType.NOTEQUAL.equals(clauseType)) {
                    predicateList.add(criteriaBuilder.notEqual(queryRoot.get(key), value));
                }
            }
        }

        if (order != null && !"".equals(order)){
            String[] orderParams = order.split(",");
            for (String o : orderParams)
                criteriaQuery.orderBy(criteriaBuilder.asc(queryRoot.get(o)));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public T GetById(Integer id) throws Exception {
        entityManager.joinTransaction();
        return entityManager.find(type, id);
    }
}
