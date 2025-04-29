package com.oriontek.resource;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.oriontek.dao.ClientDAO;
import com.oriontek.model.Client;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {
    
    private ClientDAO clientDAO = new ClientDAO();
    
    @GET
    public Response getAllClients() {
        try {
            List<Client> clients = clientDAO.getAllClients();
            return Response.ok(clients).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error getting clients: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getClientById(@PathParam("id") int id) {
        try {
            Client client = clientDAO.getClientById(id);
            if (client != null) {
                return Response.ok(client).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Client not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error getting client: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response createClient(Client client) {
        try {
            Client newClient = clientDAO.createClient(client);
            if (newClient != null) {
                return Response.status(Status.CREATED).entity(newClient).build();
            } else {
                return Response.status(Status.BAD_REQUEST).entity("Error creating client").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error creating client: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateClient(@PathParam("id") int id, Client client) {
        try {
            client.setClientId(id);
            boolean updated = clientDAO.updateClient(client);
            if (updated) {
                return Response.ok(client).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Client not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error updating client: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") int id) {
        try {
            boolean deleted = clientDAO.deleteClient(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Client not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error deleting client: " + e.getMessage()).build();
        }
    }
}