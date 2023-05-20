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
    String costumerId;
    private List<ShoppingCartItem> prods = new ArrayList<>();

    public List<ShoppingCartItem> getProds() {
        return prods;
    }

    public ShoppingCart() {}

    public ShoppingCart(String costumerId) {
        this.costumerId = costumerId;
        this.prods = new ArrayList<ShoppingCartItem>();

    }

    public ShoppingCart(String costumerId, List<ShoppingCartItem> prods) {
        this.costumerId = costumerId;
        this.prods = prods;


    }

    ShoppingCart addItem(ShoppingCartItem item, String costumerId){
        prods.add(item);
        this.costumerId = costumerId;
        return this;
    }

    ShoppingCart getCart(String costumerId, List<ShoppingCartItem> itemsList){

        return new ShoppingCart(costumerId,itemsList);
    }
}
