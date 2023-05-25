package com.cybersoft.cozastore.payload.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class BaseResponse {

    private int statusCode = 200;
    private String message = "";

    private Object data = "";
}
