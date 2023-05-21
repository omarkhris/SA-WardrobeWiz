package edu.miu.orderService.models;

import lombok.ToString;

import java.util.List;
@ToString
public class ResponseDataFromProduct {
    private boolean success;
    private List<Product2> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Product2> getData() {
        return data;
    }

    public void setData(List<Product2> data) {
        this.data = data;
    }
}