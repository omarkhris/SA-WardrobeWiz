package com.miu.cartservice.repository;

import com.miu.cartservice.entity.CartItem;
import com.miu.cartservice.other.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDao {

    @Autowired
    private RedisTemplate template;

    public void save(CartItem cart) {
        List<CartItem> list = (List<CartItem>) template.opsForHash().get(Constants.CART_HASH, cart.getUserId());
        if (list == null) {
            list = new ArrayList<CartItem>();
        }
        list.add(cart);
        template.opsForHash().put(Constants.CART_HASH, cart.getUserId(), list);
    }

    public List<CartItem> findCartByUserId(String userId) {
        List<CartItem> list = (List<CartItem>) template.opsForHash().get(Constants.CART_HASH, userId);
        if (list == null) {
            throw new RuntimeException("No data available with this user-id");
        }
        deleteCart(userId);
        return list;
    }

    public List<List<CartItem>> findAll() {
        return template.opsForHash().values(Constants.CART_HASH);
    }

    public void deleteCart(String userId) {
        template.opsForHash().delete(Constants.CART_HASH, userId);
    }
}
