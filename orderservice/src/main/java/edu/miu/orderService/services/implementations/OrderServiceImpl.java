package edu.miu.orderService.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.orderService.models.*;
import edu.miu.orderService.repositories.OrderRepository;
import edu.miu.orderService.services.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(int userId) {
        // step 1 get all the carts that this user has from cart service
        String url = "http://localhost:8888/api/cart/" + userId; // Replace with your actual endpoint URL

        ResponseEntity<Cart> response = restTemplate.exchange(url, HttpMethod.GET, null, Cart.class);
        Cart c = response.getBody();

        String url2 = "http://localhost:8080/products";
        ResponseEntity<ResponseDataFromProduct> response2 = restTemplate.exchange(url2, HttpMethod.GET, null, ResponseDataFromProduct.class);
        System.out.println(response2.getBody());

        System.out.println(c);
        for(Product p : c.getProds()){
            for(Product2 p2 : response2.getBody().getData()){
                if(p.getProdId().equals(p2.get_id()) && p.getQuantity() > p2.getQuantity()){
                    throw new RuntimeException("not enough products for product with Id + " + p.getProdId());
                }
            }
        }
        // now compute the price
        double price = 0;
        for(Product p : c.getProds()){
            for(Product2 p2 : response2.getBody().getData()){
                if(p.getProdId().equals(p2.get_id()) ){
                    price += p.getQuantity() * p2.getPrice();
                }
            }
        }
        // make a post request to reduce the quantity
        // even if the payment is not successfull because the payment will be pending, waiting to be completed
        for(Product p : c.getProds()){
            RestTemplate restTemplate = new RestTemplate();

            // Set the URL of the API endpoint
            String url4 = "http://localhost:8080/products/" + p.getProdId() ;

            // Create HttpHeaders and set the Content-Type to application/json
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            String requestBody = "{\"order_quantity\": \"" + p.getQuantity() + "\"}";

            // Create HttpEntity with headers and request body
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            restTemplate.exchange(url4, HttpMethod.POST, requestEntity, String.class).getStatusCode();

        }



        Payment p = new Payment();
        Order o = new Order(null, userId, Status.UNPAID);
        o = orderRepository.save(o);
        p.setAmount(price);
        p.setCardNumber("1234123412341234");
        p.setCardType("VISA");
        p.setOrderId(o.getOrderId().toString());
        p.setCurrency("USD");
        p.setUserId(o.getUserId());
        p.setId(null);
        String apiUrl = "http://localhost:8081/api/payment";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Payment> request = new HttpEntity<>(p, headers);

        ResponseEntity<Void> responsee = restTemplate.postForEntity(apiUrl, request, Void.class);

        if (responsee.getStatusCode().is2xxSuccessful()) {
            o.setStatus(Status.IN_TRANSIT);
            // Payment added successfully
            
            
            
            return orderRepository.save(o);
            
        } else {
            // Handle error response
        }



        return orderRepository.save(o);
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
