package no.wisdan.pgr209exam.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No order found")

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
      super(String.valueOf(message));
    }
}
