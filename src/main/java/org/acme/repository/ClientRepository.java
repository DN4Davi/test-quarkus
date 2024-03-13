package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Client;

import java.util.Optional;

@ApplicationScoped
public class ClientRepository extends Repository<Client> {

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }

    @Override
    public Optional<Client> update(Client entity) {
        var optionalEntity = readById(entity.getId());
        return optionalEntity.map((Client managed) -> {
            managed.setName(entity.getName());
            managed.setCart(entity.getCart());
            return em.merge(managed);
        });
    }
}
