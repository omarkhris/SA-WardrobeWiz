package edu.miu.orderService.services.implementations;

import edu.miu.orderService.models.Order;
import edu.miu.orderService.models.Payment;
import edu.miu.orderService.models.Status;
import edu.miu.orderService.repositories.OrderRepository;
import edu.miu.orderService.services.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@Service
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private  RestTemplate restTemplate;
    
    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(Order order) {
        order.setOrderId(null);
        order.setStatus(Status.UNPAID);
        Order o = orderRepository.save(order);
        // get user credentials(like card number) from user id 
        // here i am creating a generic payment because the user service is incomplete
        
        Payment p = new Payment();
        p.setAmount(100D);
        p.setCardNumber("1234123412341234");
        p.setCardType("VISA");
        p.setOrderId(o.getOrderId().toString());
        p.setCurrency("USD");
        p.setUserId(o.getUserId().toString());
        p.setId(null);
        String apiUrl = "http://localhost:8081/api/payment";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Payment> request = new HttpEntity<>(p, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(apiUrl, request, Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            o.setStatus(Status.IN_TRANSIT);
            return orderRepository.save(o);
            // Payment added successfully
        } else {
            // Handle error response
        }
        return o;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order deleteOrder(Long OrderId) {
        Order order = orderRepository.findById(OrderId).orElseThrow();
        orderRepository.delete(order);
        return order;
    }
    
}
