package org.acme.btask.interfaces;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.models.CustomerDto;

public interface CustomerService {
  Multi<CustomerDto> getListCustomer();
  Uni<CustomerDto> addCustomer(CustomerDto customerDto);

  Uni<CustomerDto> findByNroDocument(CustomerDto customerDto);

  Uni<CustomerDto> updateCustomer(CustomerDto customerDto);

  Uni<CustomerDto> deleteCustomer(CustomerDto customerDto);


}
