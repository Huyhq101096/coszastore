package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.entity.UserEntity;
import com.cybersoft.cozastore.payload.request.SignUpRequest;
import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.LoginService;
import com.cybersoft.cozastore.utils.JWTHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
// allow other domain call this API
@CrossOrigin
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @Autowired
    JWTHelperUtils jwtHelperUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
//        boolean isSuccess = loginService.checkLogin(username,password);
//        BaseResponse response = new BaseResponse();
//        response.setMessage(isSuccess ? "Đăng nhập thành công" : "Đăng nhập thất bại" );
//        response.setData(isSuccess);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username,password);
        authenticationManager.authenticate(user);
        String token = jwtHelperUtils.generateToken(username);
        BaseResponse response = new BaseResponse();
        response.setMessage("Đăng nhập thành công");
        response.setData(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        boolean isSuccess = loginService.insertUser(signUpRequest);
        BaseResponse response = new BaseResponse();
        response.setMessage(isSuccess ? "Thêm thành công" : "Thêm thất bại" );
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
