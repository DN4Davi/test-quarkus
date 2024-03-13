package org.acme.repository;

import jakarta.enterprise.context.Dependent;
import org.acme.entity.Product;

import java.util.Optional;

@Dependent
public class ProductRepository extends Repository<Product> {
    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    public Optional<Product> update(Product entity) {
        var entityOptional = readById(entity.getId());
        return entityOptional.map((managed) -> {
            managed.setTitle(entity.getTitle());
            managed.setPrice(entity.getPrice());
            return em.merge(managed);
        });
    }
}
