package com.netcracker.smarthome.dal.repositories;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EntityRepository<T> {
    @PersistenceContext
    protected EntityManager manager;
    private Class<T> entityClass;

    public EntityRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public T get(Object primaryKey) {
        return manager.find(entityClass, primaryKey);
    }

    @Transactional
    public List<T> getAll() {
        return manager.createQuery(String.format("select e from %s e", entityClass.getSimpleName())).getResultList();
    }

    @Transactional
    public void save(T entity) {
        manager.persist(entity);
    }

    @Transactional
    public void update(T entity) {
        manager.merge(entity);
    }

    @Transactional
    public void delete(Object primaryKey) {
        manager.remove(get(primaryKey));
    }
}
