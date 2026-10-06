package com.example.likelion14th_springboot.exception;

import com.example.likelion14th_springboot.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateMemberNameException.class)
    public ResponseEntity<ErrorResponseDto>
    handleDuplicateMemberName(
            DuplicateMemberNameException exception
    ) {
        ErrorResponseDto response =
                new ErrorResponseDto(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}