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
    private String issuerId;
    @Column(nullable = false)
    private String receiverId;
    @Column(nullable = false)
    private Long amount;


    public Payment(String issuerId, String receiverId, Long amount) {
        this.issuerId = issuerId;
        this.receiverId = receiverId;
        this.amount = amount;
    }
}
