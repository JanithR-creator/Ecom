package com.example.ecom.feanix.adviser;

import com.example.ecom.feanix.exception.DuplicateEntryException;
import com.example.ecom.feanix.exception.EntryNotFoundException;
import com.example.ecom.feanix.util.StandardResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponseDto> handleEntryNotFoundException(EntryNotFoundException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(e.getMessage(), 404, e),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<StandardResponseDto> handleDuplicateEntryException(DuplicateEntryException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(e.getMessage(), 409, e),
                HttpStatus.CONFLICT
        );
    }
}
