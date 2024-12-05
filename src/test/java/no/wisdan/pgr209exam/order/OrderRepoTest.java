package no.wisdan.pgr209exam.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 class OrderRepoTest {
     @Container
     @ServiceConnection
     static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

     @Autowired
     OrderRepo orderRepo;

     @Test
     void connectionEstablished() {
         assert postgres.isCreated();
         assert postgres.isRunning();
     }

     @Test
     void shouldInsertCustomer() {
         var order = orderRepo.save(new Order());
         assert orderRepo.findById(order.getId()).isPresent();
     }

     @Test
     void shouldDeleteCustomer() {
         var order = orderRepo.save(new Order());
         orderRepo.delete(order);
         assert orderRepo.findById(order.getId()).isEmpty();
     }
}
