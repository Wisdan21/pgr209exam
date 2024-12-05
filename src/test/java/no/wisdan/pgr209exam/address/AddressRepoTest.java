package no.wisdan.pgr209exam.address;

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
class AddressRepoTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

    @Autowired
    AddressRepo addressRepo;

    @Test
    void connectionEstablished() {
        assert postgres.isCreated();
        assert postgres.isRunning();
    }
    @Test
    void shouldInsertAddress() {
        var address = addressRepo.save(new Address());
        assert addressRepo.findById(address.getId()).isPresent();
    }
    @Test
    void shouldDeleteAddress() {
        var address = addressRepo.save(new Address());
        addressRepo.delete(address);
        assert addressRepo.findById(address.getId()).isEmpty();
    }

}
