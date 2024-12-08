package no.wisdan.pgr209exam.product;

import no.wisdan.pgr209exam.models.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

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
    void shouldInsertProduct() {
        var product = productRepo.save(new Product("Product 1", "Test Description", BigDecimal.valueOf(100), Status.AVAILABLE, 10));
        assert productRepo.findById(product.getId()).isPresent();
    }

    @Test
    void shouldDeleteProduct() {
        var product = productRepo.save(new Product("Product 2", "Test Description", BigDecimal.valueOf(200), Status.AVAILABLE, 20));
        productRepo.delete(product);
        assert productRepo.findById(product.getId()).isEmpty();
    }
    @Test
    void shouldUpdateProduct() {
        var product = productRepo.save(new Product("Product 3", "Test Description", BigDecimal.valueOf(300), Status.AVAILABLE, 30));
        product.setName("Updated Product");
        productRepo.save(product);
        assert productRepo.findById(product.getId()).get().getName().equals("Updated Product");
    }
}
