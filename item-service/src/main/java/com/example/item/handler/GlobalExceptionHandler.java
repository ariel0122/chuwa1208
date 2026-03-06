package com.example.item.handler;

import com.example.item.entity.ApiResponse;
import com.example.item.exception.BizException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return new ApiResponse(400, message, null);
    }
}