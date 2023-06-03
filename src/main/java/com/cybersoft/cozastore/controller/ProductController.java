package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {


    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getProductByCategoryId() {
        BaseResponse baseResponse = new BaseResponse();

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }
}
