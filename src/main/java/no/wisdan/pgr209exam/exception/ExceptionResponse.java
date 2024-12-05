package no.wisdan.pgr209exam.exception;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ExceptionResponse(
        int status,
        String reason,
        ZonedDateTime timestamp
){
}
