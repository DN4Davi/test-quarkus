package org.acme.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class Repository<E> {
    @Inject
    EntityManager em;

    public abstract Class<E> getEntityClass();

    public abstract Optional<E> update(E entity);

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
        var query = criteriaBuilder.createQuery(getEntityClass());
        var root = query.from(getEntityClass());
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Transactional
    public void delete(Object entityId) {
        var entityOptional = readById(entityId);
        entityOptional.ifPresentOrElse((entity) -> em.remove(entity), () -> {
            throw new NotFoundException();
        });
    }
}
