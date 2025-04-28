package com.oriontek.resource;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.oriontek.dao.CustomerDAO;
import com.oriontek.model.Customer;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    
    private CustomerDAO customerDAO = new CustomerDAO();
    
    @GET
    public Response getAllCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            return Response.ok(customers).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error getting customers: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        try {
            Customer customer = customerDAO.getCustomerById(id);
            if (customer != null) {
                return Response.ok(customer).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Customer not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error getting customer: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response createCustomer(Customer customer) {
        try {
            Customer newCustomer = customerDAO.createCustomer(customer);
            if (newCustomer != null) {
                return Response.status(Status.CREATED).entity(newCustomer).build();
            } else {
                return Response.status(Status.BAD_REQUEST).entity("Error creating customer").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error creating customer: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
        try {
            customer.setCustomerId(id);
            boolean updated = customerDAO.updateCustomer(customer);
            if (updated) {
                return Response.ok(customer).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Customer not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error updating customer: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        try {
            boolean deleted = customerDAO.deleteCustomer(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Customer not found with ID: " + id).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error deleting customer: " + e.getMessage()).build();
        }
    }
}