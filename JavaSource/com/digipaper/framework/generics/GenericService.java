package com.digipaper.framework.generics;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class GenericService<T extends GenericModel> {

    @Inject
    EntityManager entityManager;

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
            entityManager.remove(model);
        }
    }
}
