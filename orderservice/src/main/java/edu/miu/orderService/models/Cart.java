package edu.miu.orderService.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@ToString
public class Cart {
    private String customerId;
    private List<Product> prods;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Product> getProds() {
        return prods;
    }

    public void setProds(List<Product> prods) {
        this.prods = prods;
    }
}
