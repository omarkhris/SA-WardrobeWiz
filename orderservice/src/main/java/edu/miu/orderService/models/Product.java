package edu.miu.orderService.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString
public class Product {
    private String prodId;
    private int quantity;
    private String updeted;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUpdeted() {
        return updeted;
    }

    public void setUpdeted(String updeted) {
        this.updeted = updeted;
    }
}
