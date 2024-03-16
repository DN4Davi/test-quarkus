package org.acme.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;
import org.acme.entity.Client;

import java.util.Optional;

@Dependent
public class ClientRepository extends Repository<Client> {
    @Override
    public Class<Client> getEntityClass() {
        return Client.class;
    }

    @Override
    @Transactional
    public Optional<Client> update(Client entity) {
        return readById(entity.getId()).map((managedEntity) -> {
            managedEntity.setName(entity.getName());
            managedEntity.setCart(entity.getCart());
            return em.merge(managedEntity);
        });
    }
}
