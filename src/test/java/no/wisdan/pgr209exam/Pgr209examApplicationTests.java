package no.wisdan.pgr209exam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class Pgr209examApplicationTests {

    @Test
    void contextLoads() {
    }


}
