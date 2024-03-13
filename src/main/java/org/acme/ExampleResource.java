package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.CartProduct;
import org.acme.entity.Client;
import org.acme.entity.Product;
import org.acme.repository.CartProductRepository;
import org.acme.repository.ClientRepository;
import org.acme.repository.ProductRepository;

@Path("/hello")
@ApplicationScoped
public class ExampleResource {

    @Inject
    ClientRepository clientRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    CartProductRepository cartProductRepository;

    ExampleResource() {
        var newClient = new Client();
        newClient.setName("Davi");
        var newProduct = new Product();
        newProduct.setTitle("cadeira");
        newProduct.setPrice(12.0f);
        var client = clientRepository.create(newClient);
        var product = productRepository.create(newProduct);
        var newCartProduct = new CartProduct(client, product);
        var cartProduct = cartProductRepository.create(newCartProduct);
        var cartProductOptional = cartProductRepository.readById(client.getId(), product.getId());
        cartProductOptional.ifPresent((cp) -> {
            System.out.println(cp.getQuantity());
            System.out.println(cp.getClient().getName());
            System.out.println(cp.getProduct().getTitle());
        });

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }
}
