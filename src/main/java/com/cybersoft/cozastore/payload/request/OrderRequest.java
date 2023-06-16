package com.cybersoft.cozastore.payload.request;

import lombok.Data;

import java.util.List;


@Data
public class OrderRequest {

    private int countryId;

    private List<OrderProductRequest> listProduct;




}
