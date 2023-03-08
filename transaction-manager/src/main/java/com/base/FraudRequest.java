package com.base;

public record FraudRequest(String transactionId, String issuerId, String receiverId, Long amount){

}
