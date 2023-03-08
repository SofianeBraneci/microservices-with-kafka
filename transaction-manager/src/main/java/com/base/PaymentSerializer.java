package com.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Objects;

public class PaymentSerializer implements Serializer<PaymentRequest> {

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public byte[] serialize(String s, PaymentRequest payment) {
        if(Objects.isNull(payment)) return null;
        try {
            return mapper.writeValueAsBytes(payment);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing  to byte[]");
        }
    }
}
