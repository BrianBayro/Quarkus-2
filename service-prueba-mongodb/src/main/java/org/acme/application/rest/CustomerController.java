package org.acme.application.rest;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.btask.interfaces.CustomerService;
import org.acme.domain.models.CustomerDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Multi<CustomerDto> getAllCustomer() {
    return customerService.getListCustomer();
  }

  @POST
  public Uni<CustomerDto> add(CustomerDto customer) {
    return customerService.addCustomer(customer);
  }

  @POST
  @Path("/search")
  public Uni<CustomerDto> findByNroDocument(CustomerDto customerDto) {
    return customerService.findByNroDocument(customerDto);
  }

  @PUT
  public Uni<CustomerDto> updateCustomer(CustomerDto customer) {
    return customerService.updateCustomer(customer);
  }

  @DELETE
  public Uni<CustomerDto> deleteCustomer(CustomerDto customer) {
    return customerService.deleteCustomer(customer);
  }
}

