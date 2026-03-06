package com.example.paymentservice.kafka;

import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentKafkaListener {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "order-events", groupId = "payment-group")
    public void listen(OrderEvent event) {

        System.out.println("Received event: " + event);

        switch (event.getEventType()) {

            case "ORDER_CREATED":
                paymentService.createPayment(event);
                break;

            case "ORDER_CANCELLED":
                paymentService.cancelPayment(event.getOrderId());
                break;

            case "ORDER_REFUNDED":
                paymentService.refundPayment(event.getOrderId());
                break;

            case "ORDER_FINISHED":
                paymentService.finishPayment(event.getOrderId());
                break;
        }
    }
}