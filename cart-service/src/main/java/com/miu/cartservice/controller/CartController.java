package com.miu.cartservice.controller;

import com.miu.cartservice.entity.CartItem;
import com.miu.cartservice.repository.CartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartDao cartDao;

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody CartItem cart) {
        cartDao.save(cart);
        ApiResponse response = new ApiResponse(HttpStatus.CREATED.value(), "Cart item saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCart() {
        List<List<CartItem>> cartItems = cartDao.findAll();
        ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Cart items retrieved successfully", cartItems);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getCartByUserId(@PathVariable String userId) {
        List<CartItem> cartItems = cartDao.findCartByUserId(userId);
        if (cartItems.isEmpty()) {
            ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), "No cart items found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Cart items retrieved successfully", cartItems);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> removeCartByUserId(@PathVariable String userId) {
        try {
            cartDao.deleteCart(userId);
            ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Cart item deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete cart item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ApiResponse class
    @JsonInclude(Include.NON_NULL)
    public static class ApiResponse {
        private int status;
        private String message;
        private Object data;

        public ApiResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public ApiResponse(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public Object getData() {
            return data;
        }
    }
}
