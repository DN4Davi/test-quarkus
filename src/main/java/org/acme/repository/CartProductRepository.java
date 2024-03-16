package org.acme.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.acme.entity.CartProduct;

import java.util.Optional;

@Dependent
public class CartProductRepository extends Repository<CartProduct> {
    @Override
    public Class<CartProduct> getEntityClass() {
        return CartProduct.class;
    }

    @Override
    @Transactional
    public Optional<CartProduct> update(CartProduct entity) {
        var clientId = entity.getClient().getId();
        var productId = entity.getProduct().getId();
        return readById(clientId, productId).map((managedEntity) -> {
            managedEntity.setQuantity(entity.getQuantity());
            return em.merge(managedEntity);
        });
    }

    @Override
    public Optional<CartProduct> readById(Object entityId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Object entityId) {
        throw new UnsupportedOperationException();
    }

    public Optional<CartProduct> readById(Object clientId, Object productId) {
        var criteriaBuilder = em.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(getEntityClass());
        var root = query.from(getEntityClass());
        var clientJoin = root.join("client");
        var productJoin = root.join("product");
        var compareClientId = criteriaBuilder.equal(clientJoin.get("id"), clientId);
        var compareProductId = criteriaBuilder.equal(productJoin.get("id"), productId);
        query.select(root).where(criteriaBuilder.and(compareClientId, compareProductId));
        return Optional.ofNullable(em.createQuery(query).getSingleResult());
    }

    @Transactional
    public void delete(CartProduct cartProduct) {
        var clientId = cartProduct.getClient().getId();
        var productId = cartProduct.getProduct().getId();
        readById(clientId, productId).ifPresentOrElse((managedCartProduct) -> {
            em.remove(managedCartProduct);
        }, () -> {
            throw new NotFoundException();
        });
    }

    public boolean updateOrRemove(CartProduct cartProduct) {
        if (cartProduct.getQuantity() == 0) {
            delete(cartProduct);
            return true;
        } else return update(cartProduct).isPresent();
    }
}
