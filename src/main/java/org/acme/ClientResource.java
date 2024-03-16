package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Client;
import org.acme.repository.ClientRepository;

@Path("/client")
@ApplicationScoped
public class ClientResource {
    @Inject
    ClientRepository repository;

    @POST
    public Response createClient(Client client) {
        var createdClient = repository.create(client);
        return Response.status(Response.Status.CREATED).entity(createdClient).build();
    }

    @GET
    public Response getAllClients() {
        var clients = repository.readAll();
        return Response.ok(clients).build();
    }

    @GET
    @Path("/{clientId}")
    public Response getClientById(@PathParam("clientId") String clientId) {
        var clientOptional = repository.readById(clientId);
        if (clientOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(clientOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Client Not Found!").build();
    }

    @PUT
    public Response updateClient(Client client) {
        var updatedClient = repository.update(client);
        if (updatedClient.isPresent()) {
            return Response.status(Response.Status.ACCEPTED).entity(updatedClient).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Client Not Found!").build();
    }

    @DELETE
    @Path("/{clientId}")
    public Response deleteClient(@PathParam("clientId") String clientId) {
        repository.delete(clientId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
