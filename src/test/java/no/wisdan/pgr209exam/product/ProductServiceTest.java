package no.wisdan.pgr209exam.product;

import no.wisdan.pgr209exam.models.Status;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @Autowired
    ProductService service;

    @Test
    @Order(1)
    void setup() {
        for (int i = 0; i < 10; i++) {
            service.save(new Product(
                    "Product " + i,
                    "Description of product " + i,
                    BigDecimal.valueOf(100 + i),
                    Status.AVAILABLE,
                    10 + i
            ));
        }
    }

    @Test
    @Order(2)
    void getProduct() {
        List<Product> products = service.findAll();
        assert products.size() == 10;
    }

    @Test
    @Order(3)
    void getProductById() {
        var product = service.findById(1);
        assert product != null;
        assert product.getName().equals("Product 0");
    }

    @Test
    @Order(4)
    void saveProduct() {
        assert service.save(new Product("Product 110",
                "Description of Product 110",
                BigDecimal.valueOf(200),
                Status.AVAILABLE,
                20)).getName().equals("Product 110");
    }

    @Test
    @Order(5)
    void deleteProductById() {
        service.delete(1);
        assert service.findAll().size() == 10;
    }

}
