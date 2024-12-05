package no.wisdan.pgr209exam.product;

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
class ProductRepoTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

    @Autowired
    ProductRepo productRepo;

    @Test
    void connectionEstablished() {
        assert postgres.isCreated();
        assert postgres.isRunning();
    }

    @Test
    void shouldInsertLocation() {
        var product = productRepo.save(new Product());
        assert productRepo.findById(product.getId()).isPresent();
    }


    @Test
    void shouldDeleteLocation() {
        var product = productRepo.save(new Product());
        productRepo.delete(product);
        assert productRepo.findById(product.getId()).isEmpty();
    }
}
