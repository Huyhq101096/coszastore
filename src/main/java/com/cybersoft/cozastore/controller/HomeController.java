package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    // need confirm JSON return for FE to resolve

    @GetMapping("category")
    public ResponseEntity<?> getAllCategory() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(homeService.getAllCategory());

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


}
