package com.sa.shopingcart;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {


    @Autowired
    private RedisService redisService;

    @ResponseBody
    @PostMapping("/{customerId}")
    ShoppingCart addItemToShoppingCart(@RequestBody ShoppingCartItem item, @PathVariable String customerId){

        ShoppingCart myCart = null;
        try{
            //if the user did not has a cart, a null pointer exception will occur
            myCart = new ShoppingCart()
                    .getCart(customerId, StringAndList.StringToList((String) redisService.getValue(customerId)));
        }catch (NullPointerException e){
            myCart = new ShoppingCart(customerId);
        }
        //in case this user did not have a cart
        if(myCart.getProds().size() == 0){
            myCart.addItem(item, customerId);
            redisService.setValue(myCart.customerId, StringAndList.ListToString(myCart.getProds()));
            //myCart.customerId = customerId;

        }else{
            myCart.addItem(item, customerId);
            redisService.setValue(myCart.customerId, StringAndList.ListToString(myCart.getProds()));

        }
        return myCart;
    }


    @GetMapping("/{customerId}")
    @ResponseBody
    ShoppingCart getItemFromShoppingCart(@PathVariable String customerId){

        return new ShoppingCart().getCart(customerId, StringAndList.StringToList((String) redisService.getValue(customerId)));
    }


    @DeleteMapping("/{customerId}/{itemId}")
    @ResponseBody
    ShoppingCart deleteItem(@PathVariable String customerId, @PathVariable String itemId){
        //get shopping cart items from db
        List<ShoppingCartItem> items = StringAndList.StringToList((String) redisService.getValue(customerId));
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).prodId.equals(itemId)){
                items.remove(i);
            }
        }
        redisService.setValue(customerId,StringAndList.ListToString(items));
        return new ShoppingCart(customerId, items);
    }

}
