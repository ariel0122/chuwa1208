package com.example.account.controller;

import com.example.account.entity.ApiResponse;
import com.example.account.entity.User;
import com.example.account.exception.BizException;
import com.example.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ApiResponse<User> getAccount(HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(404, "User not found"));

        user.setPassword(null);

        return ApiResponse.success(user);
    }

    @PutMapping("/me")
    public ApiResponse<Void> updateAccount(HttpServletRequest request,
                                           @RequestBody User updateRequest) {

        String email = (String) request.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(404, "User not found"));

        user.setUsername(updateRequest.getUsername());
        user.setShippingAddress(updateRequest.getShippingAddress());
        user.setBillingAddress(updateRequest.getBillingAddress());
        user.setPaymentMethod(updateRequest.getPaymentMethod());

        userRepository.save(user);

        return ApiResponse.success();
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> deleteAccount(HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(404, "User not found"));

        userRepository.delete(user);

        return ApiResponse.success();
    }
}