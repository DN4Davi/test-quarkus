package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.CartProduct;
import org.acme.entity.Client;
import org.acme.entity.Product;

@Path("/hello")
public class ExampleResource {

    @GET
    public CartProduct hello() {
        Client client = new Client("Davi");
        Product product = new Product("Pizza", 48.0);
        CartProduct cartProduct = new CartProduct(client, product, 3);
        return cartProduct;
    }
}
