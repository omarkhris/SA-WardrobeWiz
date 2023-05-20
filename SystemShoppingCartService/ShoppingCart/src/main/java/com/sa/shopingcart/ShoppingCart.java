package com.sa.shopingcart;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ShoppingCart {
    String customerId;
    private List<ShoppingCartItem> prods = new ArrayList<>();

    public List<ShoppingCartItem> getProds() {
        return prods;
    }

    public ShoppingCart() {}

    public ShoppingCart(String customerId) {
        this.customerId = customerId;
        this.prods = new ArrayList<ShoppingCartItem>();

    }

    public ShoppingCart(String customerId, List<ShoppingCartItem> prods) {
        this.customerId = customerId;
        this.prods = prods;


    }

    ShoppingCart addItem(ShoppingCartItem item, String customerId){
        prods.add(item);
        this.customerId = customerId;
        return this;
    }

    ShoppingCart getCart(String customerId, List<ShoppingCartItem> itemsList){

        return new ShoppingCart(customerId,itemsList);
    }
}
