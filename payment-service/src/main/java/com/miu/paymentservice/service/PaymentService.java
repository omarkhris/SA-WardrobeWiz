package com.miu.paymentservice.service;

import com.miu.paymentservice.entity.Payment;
import com.miu.paymentservice.other.Validator;
import com.miu.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public String savePayment(Payment payment) {
        if (!Validator.isCardNumberValid(payment.getCardNumber(), payment.getCardType())) {
            throw new RuntimeException("Provided Card Information is not valid");
        }
        if (paymentRepository.findByOrderId(payment.getOrderId()).isPresent()) {
            throw new RuntimeException("Order Id already exists");
        }
        payment.setId(null);
        paymentRepository.save(payment);
        return "Payment information saved successfully";
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).orElse(null);
    }

    public List<Payment> getPaymentByUserId(String userId) {
        return paymentRepository.findByUserId(userId);
    }
}
