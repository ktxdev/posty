package com.ktxdev.facebookclone.shared.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class FacebookCloneApplicationExceptionHandler {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Error {
        private String timestamp;
        private String message;
        private int status;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            sb.append(err.getDefaultMessage()).append(System.getProperty("line.separator"));
        });
        val message = sb.substring(0, sb.lastIndexOf(System.getProperty("line.separator")));
        return Error.builder()
                .timestamp(LocalDateTime.now().toString())
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public Error handleInvalidRequestException(InvalidRequestException ex) {
        return Error.builder()
                .timestamp(LocalDateTime.now().toString())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public Error handleRecordNotFoundException(RecordNotFoundException ex) {
        return Error.builder()
                .timestamp(LocalDateTime.now().toString())
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileStorageException.class)
    public Error handleFileStorageException(FileStorageException ex) {
        return Error.builder()
                .timestamp(LocalDateTime.now().toString())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Error handleAccessDeniedException(AccessDeniedException ex) {
        return Error.builder()
                .timestamp(LocalDateTime.now().toString())
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
