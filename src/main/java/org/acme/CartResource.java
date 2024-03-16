package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AddToCartDTO;
import org.acme.entity.CartProduct;
import org.acme.repository.CartProductRepository;
import org.acme.repository.ClientRepository;

import java.net.URI;
import java.util.ArrayList;

@Path("/cart")
@ApplicationScoped
public class CartResource {
    @Inject
    CartProductRepository repository;

    @Inject
    ClientRepository clientRepository;

    @POST
    public Response addProductToCart(AddToCartDTO cartDTO) {
        var cartProduct = new CartProduct(cartDTO.clientId(), cartDTO.productId());
        var createdCartProduct = repository.create(cartProduct);
        return Response.created(getCartURI(cartDTO.clientId())).build();
    }

    @GET
    @Path("/{clientId}")
    public Response getCartByClientId(@PathParam("clientId") String clientId) {
        var clientOptional = clientRepository.readById(clientId);
        if (clientOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(clientOptional.get().getCart()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Client Not Found").build();
    }

    @PUT
    public Response updateCartProduct(CartProduct cartProduct) {
        var success = repository.updateOrRemove(cartProduct);
        if (success) {
            return Response.accepted().location(getCartURI(cartProduct.getClient().getId())).build();
        } else if (clientRepository.readById(cartProduct.getClient().getId()).isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product doesn't exist or isn't in the cart").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Client Not Found").build();

    }

    @DELETE
    @Path("/{clientId}")
    public Response clearCart(@PathParam("clientId") String clientId) {
        var clientOptional = clientRepository.readById(clientId);
        if (clientOptional.isPresent()) {
            var client = clientOptional.get();
            client.setCart(new ArrayList<>());
            clientRepository.update(client);
            return Response.status(Response.Status.NO_CONTENT).location(getCartURI(clientId)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Client Not Found").build();
    }

    private URI getCartURI(String clientId) {
        return URI.create(String.format("/cart/%s", clientId));
    }

}
