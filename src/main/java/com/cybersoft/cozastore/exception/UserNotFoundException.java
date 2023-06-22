package com.cybersoft.cozastore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private int StatusCode;


    private String message;



}
