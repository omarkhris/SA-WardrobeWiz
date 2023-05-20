package com.sa.shopingcart;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {


    @Autowired
    private RedisService redisService;

    @ResponseBody
    @PostMapping("/addItem")
    ShoppingCart addItemToShoppingCart(@RequestBody ShoppingCartItem item, @RequestParam(value = "costumerID") String costumerID){

        ShoppingCart myCart = null;
        try{
            //if the user did not has a cart, a null pointer exception will occur
            myCart = new ShoppingCart()
                    .getCart(costumerID, StringAndList.StringToList((String) redisService.getValue(costumerID)));
        }catch (NullPointerException e){
            myCart = new ShoppingCart(costumerID);
        }
        //in case this user did not have a cart
        if(myCart.getProds().size() == 0){
            myCart.addItem(item, costumerID);
            redisService.setValue(myCart.costumerId, StringAndList.ListToString(myCart.getProds()));
            //myCart.costumerId = costumerID;

        }else{
            myCart.addItem(item, costumerID);
            redisService.setValue(myCart.costumerId, StringAndList.ListToString(myCart.getProds()));

        }
        return myCart;
    }


    @GetMapping("/getCart")
    @ResponseBody
    ShoppingCart getItemFromShoppingCart(@RequestParam(value = "costumerID") String costumerId){

        return new ShoppingCart().getCart(costumerId, StringAndList.StringToList((String) redisService.getValue(costumerId)));
    }


    @DeleteMapping("/deleteItem")
    @ResponseBody
    ShoppingCart deleteItem(@RequestParam(value = "costumerID") String costumerID, @RequestParam(value = "itemId") String itemId){
        //get shopping cart items from db
        List<ShoppingCartItem> items = StringAndList.StringToList((String) redisService.getValue(costumerID));
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).prodId.equals(itemId)){
                items.remove(i);
            }
        }
        return new ShoppingCart(costumerID, items);
    }

}
