package com.example.order.controller;

import com.example.order.entity.ApiResponse;
import com.example.order.entity.Order;
import com.example.order.handler.BizException;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public ApiResponse<List<Order>> myOrders(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        if ("ADMIN".equals(role)) {
            return ApiResponse.success(service.getAllOrders());
        }

        return ApiResponse.success(
                service.getUserOrders(userId)
        );
    }

    @PostMapping
    public ApiResponse<Order> create(@RequestParam String itemId,
                                     @RequestParam Integer quantity,
                                     HttpServletRequest request) {

        System.out.println("itemId = [" + itemId + "], quantity = [" + quantity + "]");
        Long userId = (Long) request.getAttribute("userId");
        System.out.println("userId = [" + userId + "]");
        Order order = service.create(userId, itemId, quantity);

        return ApiResponse.success(order);
    }

    @PostMapping("/cancel")
    public ApiResponse<Void> cancel(
            @RequestParam UUID id,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        service.cancel(id, userId);

        return ApiResponse.success();
    }

    @PostMapping("/refund")
    public ApiResponse<Void> refund(
            @RequestParam UUID id,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        service.refund(id, userId);

        return ApiResponse.success();
    }

    @PostMapping("/finish")
    public ApiResponse<Void> finish(
            @RequestParam UUID id,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            throw new BizException(403, "Admin only");
        }

        service.finish(id);

        return ApiResponse.success();
    }
}
