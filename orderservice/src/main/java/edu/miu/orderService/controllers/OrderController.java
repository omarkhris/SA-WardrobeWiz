package edu.miu.orderService.controllers;

import java.util.List;

import edu.miu.orderService.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.miu.orderService.models.Order;
import edu.miu.orderService.models.Payment;
import edu.miu.orderService.services.OrderService;
import edu.miu.orderService.services.RabbitMQSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitMQSenderService rabbitMQSenderService;

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/{userId}")
    public Order createOrder(@PathVariable int userId, @RequestHeader("Authorization") String token) {
        Order order = orderService.createOrder(userId);

        // Send mail for created order
        // Remove the "Bearer " prefix from the token
        token = token.replace("Bearer ", "");

        String email = JwtUtil.extractEmailFromToken(token);

        rabbitMQSenderService.sendOrderConfirmation(
                "sender@example.com",
                List.of(email),
                "Order Confirmation",
                "Your order has been created",
                List.of()
        );

        return order;
    }

    @PostMapping("/{orderId}/delivery")
    public ResponseEntity<String> sendDeliveryEmail(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        // Send mail for delivery order
        rabbitMQSenderService.sendDeliveryConfirmation(
                "sender@example.com",
                List.of("receiver@example.com","receiver1@example.com"),
                "Delivery Confirmation",
                "Your order has been delivered",
                List.of()
        );

        return ResponseEntity.ok("Delivery email sent successfully.");
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{orderId}")
    public Order deleteOrder(@PathVariable Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
