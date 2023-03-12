package com.payment;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;


import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class PaymentRequestDeserializer implements Deserializer<PaymentRequest> {

    public PaymentRequestDeserializer() {
    }

    @Override
    public PaymentRequest deserialize(String s, byte[] bytes) {
       try{
           if(Objects.isNull(bytes)){
               return null;
           }
           ObjectMapper mapper = new ObjectMapper();
           mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
           return mapper.readValue(new String(bytes), PaymentRequest.class);
       }catch (Exception e ){
           e.printStackTrace();
           throw new SerializationException("Error when deserializing byte[]");

       }
    }
}
