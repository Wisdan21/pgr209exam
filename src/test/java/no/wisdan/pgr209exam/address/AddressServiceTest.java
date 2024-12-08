package no.wisdan.pgr209exam.address;

import no.wisdan.pgr209exam.customer.Customer;
import no.wisdan.pgr209exam.customer.CustomerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressServiceTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @Autowired
    AddressService service;

    @Autowired
    CustomerService customerService;

    @Test
    @Order(1)
    void setup() {
        Customer customer = customerService.save(new Customer("First", "Last", "123456789", "email"));
        List<Long> customerIds = List.of(customer.getId());
        for (int i = 0; i < 10; i++) {
            service.save(new AddressDto(
                    "Street " + i,
                    "City " + i,
                    "Zipcode " + (1000 + i),
                    customerIds
            ));
        }
    }

    @Test
    @Order(3)
    void saveAddress() {
        List<Customer> customers = customerService.findAll();
        AddressDto addressDto = new AddressDto("Street 1", "City 1", "Zipcode 1", List.of(customers.get(0).getId()));
        Address savedAddress = service.save(addressDto);
        assert savedAddress.getStreet().equals("Street 1");
    }
}
