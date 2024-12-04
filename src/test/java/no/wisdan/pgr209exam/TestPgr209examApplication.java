package no.wisdan.pgr209exam;

import org.springframework.boot.SpringApplication;

public class TestPgr209examApplication {

    public static void main(String[] args) {
        SpringApplication.from(Pgr209examApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
