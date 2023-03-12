package com.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentListener {

    @Autowired
    private PaymentRepository repository;

    @KafkaListener(topics = "payments", groupId = "payment-agents")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void paymentProcessor(@Payload PaymentRequest request) throws JsonProcessingException {
        System.out.println(request);
        repository.save(new Payment(request.from(), request.to(), request.amount()));
    }
}
