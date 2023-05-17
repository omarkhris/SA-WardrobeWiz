package com.miu.paymentservice.controller;

import com.miu.paymentservice.entity.Payment;
import com.miu.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/save")
    public void addPayment(@RequestBody Payment payment) {
        paymentService.savePayment(payment);
    }

    @GetMapping("/get")
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/get/{userId}")
    public List<Payment> getPaymentByUserId(@PathVariable String userId) {
        return paymentService.getPaymentByUserId(userId);
    }
}
