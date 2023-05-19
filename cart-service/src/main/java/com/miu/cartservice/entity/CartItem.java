package com.miu.cartservice.entity;

import com.miu.cartservice.other.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(Constants.CART_HASH)
public class CartItem implements Serializable {
    private String userId;
    private String productId;
    private Integer quantity;
}
