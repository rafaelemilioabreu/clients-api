package com.oriontek.resource;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.oriontek.dao.AddressDAO;
import com.oriontek.model.Address;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {
    
    private AddressDAO addressDAO = new AddressDAO();
    
    @GET
    public Response getAllAddresses() {
        try {
            List<Address> addresses = addressDAO.getAllAddresses();
            return Response.ok(addresses).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error getting addresses: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getAddressById(@PathParam("id") int id) {
        try {
            Address address = addressDAO.getAddressById(id);
            if (address != null) {
                return Response.ok(address).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Address not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error getting address: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/client/{clientId}")
    public Response getAddressesByClientId(@PathParam("clientId") int clientId) {
        try {
            List<Address> addresses = addressDAO.getAddressesByClientId(clientId);
            return Response.ok(addresses).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error getting addresses: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response createAddress(Address address) {
        try {
            Address newAddress = addressDAO.createAddress(address);
            if (newAddress != null) {
                return Response.status(Status.CREATED).entity(newAddress).build();
            } else {
                return Response.status(Status.BAD_REQUEST).entity("Error creating address").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error creating address: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateAddress(@PathParam("id") int id, Address address) {
        try {
            address.setAddressId(id);
            boolean updated = addressDAO.updateAddress(address);
            if (updated) {
                return Response.ok(address).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Address not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error updating address: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") int id) {
        try {
            boolean deleted = addressDAO.deleteAddress(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Address not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error deleting address: " + e.getMessage()).build();
        }
    }
}