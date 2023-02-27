package org.acme.infraestructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.contract.CustomerRepository;
import org.acme.domain.models.CustomerDto;
import org.acme.infraestructure.entity.Customer;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@ApplicationScoped
public class CustomerRepositoryImpl implements CustomerRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public CustomerRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }

  @Override
  public Multi<CustomerDto> getListCustomer() {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("customers");
    ReactiveMongoCollection<Document> collection = database.getCollection("customer");

    return collection.find().map(doc->{
      CustomerDto customer = new CustomerDto();
      customer.setName(doc.getString("name"));
      customer.setLastName(doc.getString("lastName"));
      customer.setNroDocument(doc.getLong("nroDocument"));
      customer.setTypeCustomer(doc.getInteger("typeCustomer"));
      customer.setTypeDocument(doc.getInteger("typeDocument"));
      customer.setCreated_datetime(doc.getString("created_datetime"));
      customer.setUpdated_datetime(doc.getString("updated_datetime"));
      customer.setActive(doc.getString("active"));
      return customer;
    }).filter(c->c.getActive().equals("S"));
  }
  @Override
  public Uni<CustomerDto> findByNroDocument(CustomerDto customerDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("customers");
    ReactiveMongoCollection<Document> collection = database.getCollection("customer");
    return collection
            .find(new Document("nroDocument", customerDto.getNroDocument())).map(doc->{
              CustomerDto customer = new CustomerDto();
              customer.setName(doc.getString("name"));
              customer.setLastName(doc.getString("lastName"));
              customer.setNroDocument(doc.getLong("nroDocument"));
              customer.setTypeCustomer(doc.getInteger("typeCustomer"));
              customer.setTypeDocument(doc.getInteger("typeDocument"));
              customer.setCreated_datetime(doc.getString("created_datetime"));
              customer.setUpdated_datetime(doc.getString("updated_datetime"));
              customer.setActive(doc.getString("active"));
              return customer;
            }).filter(s->s.getActive().equals("S")).toUni();
  }


  @Override
  public Uni<CustomerDto> addCustomer(CustomerDto customerDto) {
    Document document = new Document()
            .append("name", customerDto.getName())
            .append("lastName", customerDto.getLastName())
            .append("nroDocument", customerDto.getNroDocument())
            .append("typeCustomer", customerDto.getTypeCustomer())
            .append("typeDocument", customerDto.getTypeDocument())
            .append("created_datetime", this.getDateNow())
            .append("updated_datetime", this.getDateNow())
            .append("active", "S");


    return getCollection().insertOne(document).replaceWith(customerDto);

  }

  @Override
  public Uni<CustomerDto> updateCustomer(CustomerDto customerDto){


    Bson update = combine(
            set("name", customerDto.getName()),
            set("active", "S"),
            set("updated_datetime", this.getDateNow()));


    return getCollection().findOneAndUpdate(new Document("nroDocument", customerDto.getNroDocument()), update)
            .onItem().transform(doc -> {
              CustomerDto customer = new CustomerDto();
                customer.setName(doc.getString("name"));
                customer.setLastName(doc.getString("lastName"));
                customer.setNroDocument(doc.getLong("nroDocument"));
                customer.setTypeCustomer(doc.getInteger("typeCustomer"));
                customer.setTypeDocument(doc.getInteger("typeDocument"));
                customer.setCreated_datetime(doc.getString("created_datetime"));
                customer.setUpdated_datetime(doc.getString("updated_datetime"));
                customer.setActive(doc.getString("active"));
              return customer;
            });

  }

  @Override
  public Uni<CustomerDto> deleteCustomer(CustomerDto customerDto) {
    Bson filter = eq("nroDocument", customerDto.getNroDocument());
    Bson update = set("active", "N");
    System.out.println(customerDto.getTypeCustomer());

    return getCollection().updateMany(filter,update).replaceWith(customerDto);
  }

  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }

  public static Customer mapToEntity(Object customerDto) {
    return new ObjectMapper().convertValue(customerDto, Customer.class);
  }
  public static CustomerDto mapToDto(Object customerDto) {
    return new ObjectMapper().convertValue(customerDto, CustomerDto.class);
  }

  public static CustomerDto mapToDomain(Customer entity) {
    return new ObjectMapper().convertValue(entity, CustomerDto.class);
  }

  private ReactiveMongoCollection<Document> getCollection() {
    return reactiveMongoClient.getDatabase("customers").getCollection("customer");
  }
}
