package no.wisdan.pgr209exam.customer;

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
class CustomerRepoTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

    @Autowired
    CustomerRepo customerRepo;

    @Test
    void connectionEstablished() {
        assert postgres.isCreated();
        assert postgres.isRunning();
    }

    @Test
    void shouldInsertCustomer() {
        var customer = customerRepo.save(new Customer());
        assert customerRepo.findById(customer.getId()).isPresent();
    }

    @Test
    void shouldDeleteCustomer() {
        var customer = customerRepo.save(new Customer());
        customerRepo.delete(customer);
        assert customerRepo.findById(customer.getId()).isEmpty();
    }
}
