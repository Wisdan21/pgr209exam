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
        for (int i = 0; i < 10; i++) {
            service.save(new Address(
                    "Street " + i,
                    "City " + i,
                    "Zipcode " + (1000 + i),
                    customer
            ));
        }
    }

    @Test
    @Order(2)
    void getAddresses() {
        List<Address> addresses = service.findAll();
        assert addresses.size() == 10;
    }
    @Test
    @Order(3)
    void getAddressById() {
        var address = service.findById(1);
        assert address != null;
        assert address.getStreet().equals("Street 0");
    }

    @Test
    @Order(4)
    void saveAddress() {
        Address savedAddress = service.save(new Address("Street 1", "City 1", "Zipcode 1", customerService.findAll().get(0)));
        assert savedAddress.getStreet().equals("Street 1");
    }

    @Test
    @Order(5)
    void deleteAddressById() {
        service.deleteById(1);
        assert service.findAll().size() == 10;
    }

}
