package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public abstract class Repository<E> {
    @Inject
    protected EntityManager em;

    protected abstract Class<E> getEntityClass();

    @Transactional
    public E create(E entity) {
        em.persist(entity);
        return entity;
    }

    public Optional<E> readById(Object entityId) {
        return Optional.ofNullable(em.find(getEntityClass(), entityId));
    }

    public List<E> readAll() {
        var criteriaBuilder = em.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        var root = criteriaQuery.from(getEntityClass());
        criteriaQuery.select(root);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    public abstract Optional<E> update(E entity);

    @Transactional
    public void delete(Object entityId) {
        var entityOptional = readById(entityId);
        if (entityOptional.isPresent()) {
            em.remove(entityOptional.get());
        } else {
            throw new IllegalArgumentException();
        }
    }

}
