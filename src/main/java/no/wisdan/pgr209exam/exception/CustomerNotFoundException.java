package no.wisdan.pgr209exam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No customer found")

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(String.valueOf(message));
    }
}
