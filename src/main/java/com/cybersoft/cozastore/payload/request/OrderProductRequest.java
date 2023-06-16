package com.cybersoft.cozastore.payload.request;


import lombok.Data;

@Data
public class OrderProductRequest {
    private int id;
    private double price;
    private int quantity;

}
