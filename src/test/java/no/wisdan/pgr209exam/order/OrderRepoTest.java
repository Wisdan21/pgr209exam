package no.wisdan.pgr209exam.order;

import no.wisdan.pgr209exam.address.Address;
import no.wisdan.pgr209exam.address.AddressRepo;
import no.wisdan.pgr209exam.customer.Customer;
import no.wisdan.pgr209exam.customer.CustomerRepo;
import no.wisdan.pgr209exam.models.Status;
import no.wisdan.pgr209exam.product.Product;
import no.wisdan.pgr209exam.product.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Collections;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 class OrderRepoTest {
     @Container
     @ServiceConnection
     static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

     @Autowired
     OrderRepo orderRepo;


    @Autowired
    ProductRepo productRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AddressRepo addressRepo;

     @Test
     void connectionEstablished() {
         assert postgres.isCreated();
         assert postgres.isRunning();
     }

     @Test
     void shouldInsertOrder() {
         var product = productRepo.save(new Product("Product 1", "Test Product", BigDecimal.valueOf(100), Status.AVAILABLE, 10));
         var customer = customerRepo.save(new Customer("First", "Last", "123456789", "email@example.com"));
         var address = addressRepo.save(new Address("123 Street", "City", "12345", Collections.singletonList(customer)));
         var order = orderRepo.save(new Order(BigDecimal.valueOf(10), BigDecimal.valueOf(110), false, customer, Collections.singletonList(product), address));

         assert orderRepo.findById(order.getId()).isPresent();
     }

    @Test
     void shouldDeleteOrder() {
         var order = orderRepo.save(new Order());
         orderRepo.delete(order);
         assert orderRepo.findById(order.getId()).isEmpty();
     }
}
