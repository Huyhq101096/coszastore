package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.response.ProductResponse;
import com.cybersoft.cozastore.repository.ProductRepository;
import com.cybersoft.cozastore.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getProductByCategoryId(int id) {
        List<ProductEntity> list = productRepository.findByCategoryId(id);
        List<ProductResponse> productResponsesList = new ArrayList<>();
        for (ProductEntity product : list) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setImage(product.getImage());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponsesList.add(productResponse);
        }
        return productResponsesList;
    }
}
