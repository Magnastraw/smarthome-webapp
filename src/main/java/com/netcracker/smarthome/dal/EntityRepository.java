package com.netcracker.smarthome.dal;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class EntityRepository<T> {
    @PersistenceContext
    protected EntityManager manager;
    protected Class<T> entityClass;

    public EntityRepository() {
        setEntityClass();
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
    public void create(T entity) {
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

    public abstract void setEntityClass();
}
