package com.example.order.service;

import com.example.order.client.ItemClient;
import com.example.order.entity.ApiResponse;
import com.example.order.entity.ItemDTO;
import com.example.order.entity.Order;
import com.example.order.handler.BizException;
import com.example.order.kafka.OrderEvent;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order create(Long userId, String itemId, Integer quantity) {

        ApiResponse<ItemDTO> itemResponse = itemClient.getItem(itemId);

        if (itemResponse.getCode() != 200) {
            throw new BizException(itemResponse.getCode(), itemResponse.getMessage());
        }

        ItemDTO item = itemResponse.getData();

        if (item.getInventory() <= quantity) {
            throw new BizException(400, "Not enough stock");
        }


        ApiResponse<Void> response = itemClient.reduce(itemId, quantity);

        if (response.getCode() != 200) {
            throw new BizException(response.getCode(), response.getMessage());
        }

        BigDecimal amount = item.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(userId);
        order.setItemId(itemId);
        order.setItemName(item.getName());
        order.setPrice(item.getPrice());
        order.setQuantity(quantity);
        order.setAmount(amount);
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());

        repository.save(order);

        System.out.println("order = [" + order + "]");

        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getUserId(),
                order.getAmount(),
                "ORDER_CREATED"
        );

        kafkaTemplate.send("order-events", event);

        return order;
    }

    public ApiResponse<Void> cancel(UUID id, Long userId) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new BizException(404, "Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new BizException(403, "Not your order");
        }

        if ("CANCELLED".equals(order.getStatus())) {
            throw new BizException(400, "Order already cancelled");
        }

        if (!"CREATED".equals(order.getStatus())) {
            throw new BizException(400, "Order cannot be cancelled");
        }

        order.setStatus("CANCELLED");
        repository.save(order);

        itemClient.increase(order.getItemId(), order.getQuantity());

        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getUserId(),
                null,
                "ORDER_CANCELLED"
        );

        kafkaTemplate.send("order-events", event);

        return ApiResponse.success();
    }

    public List<Order> getUserOrders(Long userId) {

        return repository.findByUserId(userId);
    }

    public ApiResponse<Void> refund(UUID id, Long userId) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new BizException(404, "Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new BizException(403, "Not your order");
        }

        if (!"PAID".equals(order.getStatus())) {
            throw new BizException(400, "Only PAID order can refund");
        }

        order.setStatus("REFUNDED");

        repository.save(order);

        itemClient.increase(order.getItemId(), order.getQuantity());

        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getUserId(),
                order.getAmount(),
                "ORDER_REFUNDED"
        );

        kafkaTemplate.send("order-events", event);

        return ApiResponse.success();
    }

    public ApiResponse<Void> finish(UUID id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new BizException(404, "Order not found"));

        if (!"PAID".equals(order.getStatus())) {
            throw new BizException(400, "Only PAID order can finish");
        }

        order.setStatus("FINISHED");

        repository.save(order);

        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getUserId(),
                order.getAmount(),
                "ORDER_FINISHED"
        );

        kafkaTemplate.send("order-events", event);

        return ApiResponse.success();
    }
}
