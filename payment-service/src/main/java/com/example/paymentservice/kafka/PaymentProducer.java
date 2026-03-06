package com.example.paymentservice.kafka;

        import lombok.RequiredArgsConstructor;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.kafka.core.KafkaTemplate;
        import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendPaymentSuccess(String orderId) {

        String message = "PAYMENT_SUCCESS:" + orderId;

        kafkaTemplate.send("payment-events", message);
    }
}