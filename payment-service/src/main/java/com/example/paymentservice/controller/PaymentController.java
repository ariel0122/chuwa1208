package com.example.paymentservice.controller;

import com.example.paymentservice.entity.ApiResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Get all payments for the current user
     */
    @GetMapping
    public ApiResponse<List<Payment>> myPayments(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        return ApiResponse.success(
                paymentService.getUserPayments(userId)
        );
    }

    /**
     * Process payment for an order
     */
    @PostMapping("/pay")
    public ApiResponse<Void> pay(@RequestParam String orderId,
                                 HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        paymentService.pay(orderId, userId);

        return ApiResponse.success();
    }
}