package com.cybersoft.cozastore.exception;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileNotFoundException extends RuntimeException {

    private String message;



}
