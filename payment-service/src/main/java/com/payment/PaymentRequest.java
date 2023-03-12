package com.payment;

public record PaymentRequest(String from, String to, Long amount) {
}
