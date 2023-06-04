package com.cybersoft.cozastore.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private String image;
    private String name;
    private double price;
}
