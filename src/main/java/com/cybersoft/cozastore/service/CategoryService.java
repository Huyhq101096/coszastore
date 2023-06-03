package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.payload.response.CategoryResponse;
import com.cybersoft.cozastore.repository.CategoryRepository;
import com.cybersoft.cozastore.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<CategoryResponse> getAllCategory() {



        // get data from database
        List<CategoryEntity> list = categoryRepository.findAll();

        // give data for FE
        List<CategoryResponse> responseList = new ArrayList<>();
        for(CategoryEntity categoryEntity: list) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(categoryEntity.getId());
            categoryResponse.setName(categoryEntity.getName());
            responseList.add(categoryResponse);

        }

        return responseList;
    }
}
