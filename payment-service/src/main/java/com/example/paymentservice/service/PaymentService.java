package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.kafka.OrderEvent;
import com.example.paymentservice.kafka.PaymentProducer;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentProducer paymentProducer;


    // 1 Create payment
    public void createPayment(OrderEvent event) {

        Payment payment = new Payment();

        payment.setOrderId(event.getOrderId().toString());
        payment.setUserId(event.getUserId());
        payment.setAmount(event.getAmount().doubleValue());
        payment.setStatus("CREATED");

        System.out.println("payment = [" + payment + "]");
        paymentRepository.save(payment);
    }

    // 2 Process user payment
    public void pay(String orderId, Long userId) {

        Payment payment = paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getUserId().equals(userId)) {
            throw new RuntimeException("Not your order");
        }

        if (!"CREATED".equals(payment.getStatus())) {
            throw new RuntimeException("Cannot pay - payment is not in CREATED status");
        }

        payment.setStatus("PAID");

        paymentRepository.save(payment);

        paymentProducer.sendPaymentSuccess(orderId);
    }

    // 3 Cancel order payment
    public void cancelPayment(UUID orderId) {

        paymentRepository.findByOrderId(orderId.toString())
                .ifPresent(payment -> {

                    payment.setStatus("CANCELLED");

                    paymentRepository.save(payment);
                });
    }

    // 4 Refund order payment
    public void refundPayment(UUID orderId) {

        paymentRepository.findByOrderId(orderId.toString())
                .ifPresent(payment -> {

                    payment.setStatus("REFUNDED");

                    paymentRepository.save(payment);
                });
    }

    // 5 Complete order payment
    public void finishPayment(UUID orderId) {

        paymentRepository.findByOrderId(orderId.toString())
                .ifPresent(payment -> {

                    payment.setStatus("FINISHED");

                    paymentRepository.save(payment);
                });
    }

    public List<Payment> getUserPayments(Long userId) {

        return paymentRepository.findByUserId(userId);
    }
}