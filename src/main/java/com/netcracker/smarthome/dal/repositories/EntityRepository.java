package com.netcracker.smarthome.dal.repositories;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EntityRepository<T> {
    @PersistenceContext
    private EntityManager manager;
    private Class<T> entityClass;

    public EntityRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getManager() {
        return manager;
    }

    public T get(Object primaryKey) {
        return getManager().find(entityClass, primaryKey);
    }

    public List<T> getAll() {
        return getManager().createQuery(String.format("select e from %s e", entityClass.getSimpleName())).getResultList();
    }

    @Transactional
    public void save(T entity) {
        getManager().persist(entity);
    }

    @Transactional
    public T update(T entity) {
        return getManager().merge(entity);
    }

    @Transactional
    public void delete(Object primaryKey) {
        getManager().remove(get(primaryKey));
    }
}
