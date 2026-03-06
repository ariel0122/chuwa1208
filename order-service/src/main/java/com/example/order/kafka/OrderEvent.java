package com.example.order.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderEvent {

    private UUID orderId;
    private Long userId;
    private BigDecimal amount;

    private String eventType;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public OrderEvent() {
    }

    public OrderEvent(UUID orderId, Long userId, BigDecimal amount, String eventType) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.eventType = eventType;
    }
}