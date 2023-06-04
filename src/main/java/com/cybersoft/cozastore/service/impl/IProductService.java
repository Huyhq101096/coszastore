package com.cybersoft.cozastore.service.impl;


import com.cybersoft.cozastore.payload.response.ProductResponse;

import java.util.List;

public interface IProductService {

    List<ProductResponse> getProductByCategoryId(int id);

}
