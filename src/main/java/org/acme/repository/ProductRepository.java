package org.acme.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;
import org.acme.entity.Product;

import java.util.Optional;

@Dependent
public class ProductRepository extends Repository<Product> {
    @Override
    public Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    @Transactional
    public Optional<Product> update(Product entity) {
        return readById(entity.getId()).map((managedEntity) -> {
            managedEntity.setTitle(entity.getTitle());
            managedEntity.setPrice(entity.getPrice());
            return em.merge(managedEntity);
        });
    }
}
