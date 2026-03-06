package com.example.paymentservice.kafka;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderEvent {

    private String eventType;

    private UUID orderId;

    private Long userId;

    private BigDecimal amount;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

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

    public OrderEvent() {
    }

    public OrderEvent(String eventType, UUID orderId, Long userId, BigDecimal amount) {
        this.eventType = eventType;
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }
}