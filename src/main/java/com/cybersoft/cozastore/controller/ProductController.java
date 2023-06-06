package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.request.ProductRequest;
import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByCategoryId(@PathVariable("id") int id) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(iProductService.getProductByCategoryId(id));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@Valid @RequestParam ProductRequest productRequest) {

        return new ResponseEntity<>("",HttpStatus.OK);
    }
}
