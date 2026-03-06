package com.example.order.kafka;

import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderKafkaListener {

    @Autowired
    private OrderRepository repository;

    @KafkaListener(
            topics = "payment-events",
            groupId = "order-service"
    )
    public void handlePayment(String message) {

        System.out.println("message = [" + message + "]");

        if (message.contains("PAYMENT_SUCCESS")) {

            String orderIdStr  = message.split(":")[1].replace("\"", "");

            UUID orderId = UUID.fromString(orderIdStr);

            System.out.println("orderId = [" + orderId + "]");

            Order order = repository.findById(orderId).orElse(null);

            if (order == null) {
                return;
            }

            order.setStatus("PAID");
            repository.save(order);
        }
    }

}
