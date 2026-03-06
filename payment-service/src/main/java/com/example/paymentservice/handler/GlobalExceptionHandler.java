package com.example.paymentservice.handler;

import com.example.paymentservice.entity.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse<Void> handleTypeMismatch(MethodArgumentTypeMismatchException e) {

        return new ApiResponse<>(
                400,
                "Invalid parameter: " + e.getName(),
                null
        );
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<?> handleBiz(BizException e) {

        return ResponseEntity
                .status(e.getCode())
                .body(Map.of(
                        "code", e.getCode(),
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception e) {

        return ResponseEntity
                .status(500)
                .body(Map.of(
                        "code", 500,
                        "message", "Internal Server Error"
                ));
    }
}