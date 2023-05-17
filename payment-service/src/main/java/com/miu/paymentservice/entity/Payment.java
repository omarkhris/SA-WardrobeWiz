package com.miu.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String orderId;
    private Double amount;
    private String currency;
    private String cardNumber;
    private String cardType;
}
