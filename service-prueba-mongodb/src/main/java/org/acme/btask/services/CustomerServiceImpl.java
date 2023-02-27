package org.acme.btask.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.btask.interfaces.CustomerService;
import org.acme.domain.contract.CustomerRepository;
import org.acme.domain.models.CustomerDto;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Multi<CustomerDto> getListCustomer() {
    return customerRepository.getListCustomer();
  }
  @Override
  public Uni<CustomerDto> addCustomer(CustomerDto customerDto) {
    return customerRepository.addCustomer(customerDto);
  }

  @Override
  public Uni<CustomerDto> findByNroDocument(CustomerDto customerDto) {
    return customerRepository.findByNroDocument(customerDto);
  }

  @Override
  public Uni<CustomerDto> updateCustomer(CustomerDto customerDto) {
    return customerRepository.updateCustomer(customerDto);
  }

  @Override
  public Uni<CustomerDto> deleteCustomer(CustomerDto customerDto) {
    return customerRepository.deleteCustomer(customerDto);
  }


}
