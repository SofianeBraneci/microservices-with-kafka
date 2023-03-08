package com.base;

public record ServiceRequest(String transactionId, String issuerId, String receiverId, Long amount) { }
