package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.CartProduct;
import org.acme.entity.Client;
import org.acme.entity.Product;

import java.util.Optional;

@ApplicationScoped
public class CartProductRepository extends Repository<CartProduct> {

    @Override
    protected Class<CartProduct> getEntityClass() {
        return CartProduct.class;
    }

    public Optional<CartProduct> readById(String clientId, String productId) {
        var client = new Client();
        client.setId(clientId);
        var product = new Product();
        product.setId(productId);
        var criteriaBuilder = em.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(getEntityClass());
        var root = query.from(getEntityClass());
        var clientPath = root.get("client").as(Client.class);
        var productPath = root.get("product").as(Product.class);
        query.select(root).where(criteriaBuilder.and(criteriaBuilder.equal(clientPath, client), criteriaBuilder.equal(productPath, product)));
        return Optional.ofNullable(em.createQuery(query).getSingleResult());
    }

    @Override
    public Optional<CartProduct> update(CartProduct entity) {
        return Optional.empty();
    }
}
