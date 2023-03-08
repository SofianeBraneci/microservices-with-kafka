package com.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;
    @Column(nullable = false)
    private String transactionId;
    @Column(nullable = false)
    private String issuerId;
    @Column(nullable = false)
    private String receiverId;
    @Column(nullable = false)
    private String issuerAccountId;
    @Column(nullable = false)
    private String receiverAccountId;




}
