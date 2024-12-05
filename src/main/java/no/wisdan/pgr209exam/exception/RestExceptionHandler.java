package no.wisdan.pgr209exam.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCustomerNotFound() {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .reason("Customer not found")
                .timestamp(ZonedDateTime.now(ZoneId.of("Europe/Oslo")))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOrderNotFound() {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .reason("Order not found")
                .timestamp(ZonedDateTime.now(ZoneId.of("Europe/Oslo")))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFound() {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .reason("Product not found")
                .timestamp(ZonedDateTime.now(ZoneId.of("Europe/Oslo")))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
