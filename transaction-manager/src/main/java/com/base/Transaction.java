package com.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private String transactionId;
    private String issuerId;
    private String receiverId;
    private String issuerAccountId;
    private String receiverAccountId;
    private Long amount;

    public Transaction(String issuerId, String receiverId, String issuerAccountId, String receiverAccountId, Long amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.issuerId = issuerId;
        this.receiverId = receiverId;
        this.issuerAccountId = issuerAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }
}
