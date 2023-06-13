package com.cybersoft.cozastore.payload.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ColorResponse {

    private int id;

    private String name;
}
