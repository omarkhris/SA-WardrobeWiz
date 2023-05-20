package com.miu.cartservice.controller;

import com.miu.cartservice.entity.CartItem;
import com.miu.cartservice.repository.CartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartDao cartDao;

    @PostMapping
    public void save(@RequestBody CartItem cart) {
        cartDao.save(cart);
    }

    @GetMapping
    public List<List<CartItem>> getAllCart() {
        return cartDao.findAll();
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCartByUserId(@PathVariable String userId) {
        return cartDao.findCartByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    public void removeCartByUserId(@PathVariable String userId) {
        cartDao.deleteCart(userId);
    }

}
