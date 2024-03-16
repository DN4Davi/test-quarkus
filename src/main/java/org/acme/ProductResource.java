package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Product;
import org.acme.repository.ProductRepository;

@Path("/product")
@ApplicationScoped
public class ProductResource {
    @Inject
    ProductRepository repository;

    @POST
    public Response createProduct(Product newProduct) {
        var product = repository.create(newProduct);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @GET
    public Response getAllProducts() {
        var products = repository.readAll();
        return Response.status(Response.Status.OK).entity(products).build();
    }

    @GET
    @Path("/{productId}")
    public Response getProductById(@PathParam("productId") String productId) {
        var productOptional = repository.readById(productId);
        if (productOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(productOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Product Not Found").build();
    }

    @PUT
    public Response updateProduct(Product product) {
        var updatedProduct = repository.update(product);
        if (updatedProduct.isPresent()) {
            return Response.status(Response.Status.ACCEPTED).entity(updatedProduct.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Product Not Found").build();
    }

    @DELETE
    @Path("/{productId}")
    public Response deleteProduct(@PathParam("productId") String productId) {
        repository.delete(productId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
