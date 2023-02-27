package org.acme.domain.contract;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.models.CustomerDto;

public interface CustomerRepository {
  Multi<CustomerDto> getListCustomer();
  Uni<CustomerDto> findByNroDocument(CustomerDto customerDto);
  Uni<CustomerDto> addCustomer(CustomerDto customerDto);

  Uni<CustomerDto> updateCustomer(CustomerDto customerDto);

  Uni<CustomerDto> deleteCustomer(CustomerDto customerDto);

}
