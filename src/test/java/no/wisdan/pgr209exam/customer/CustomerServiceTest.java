package no.wisdan.pgr209exam.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerServiceTest {
    @Autowired
    CustomerService service;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @Test
    void connectionEstablished() {
        assert postgreSQLContainer.isCreated();
        assert postgreSQLContainer.isRunning();
    }

    @Test
    @Order(1)
    void setup() {
        for (int i = 0; i < 10; i++) {
            service.save(new Customer("First" + i, "Last" + i, "123456789", "email" + i));
        }
    }

    @Test
    @Order(3)
    void saveCustomer() {
        var customer = service.save(new Customer("FirstN", "LastN", "123456789", "emailN"));
        assert customer.getFirstName().equals("FirstN");
    }
}