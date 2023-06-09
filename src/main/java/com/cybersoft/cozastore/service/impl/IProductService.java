package com.cybersoft.cozastore.service.impl;


import com.cybersoft.cozastore.payload.request.ProductRequest;
import com.cybersoft.cozastore.payload.response.ProductResponse;

import java.util.List;

public interface IProductService {

    List<ProductResponse> getProductByCategoryId(int id, String hostName);

    ProductResponse getDetailProduct(int id);

    boolean addProduct(ProductRequest productRequest);

    boolean clearCache();

}
