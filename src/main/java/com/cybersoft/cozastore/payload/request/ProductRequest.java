package com.cybersoft.cozastore.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Name cannot be left blank")
    private String name;
    @DecimalMin(value = "0.1")
    private double price;

    private String desc;
    private int quantity;
    private int sizeId;
    private int colorId;
    private int categoryId;

}
