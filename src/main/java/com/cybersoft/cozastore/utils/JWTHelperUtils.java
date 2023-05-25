package com.cybersoft.cozastore.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTHelperUtils {
    //    @Value : Giúp lấy key khai báo trên file applicationproperties
    @Value("${jwt.token.key}")
    String secretKey;


    /*
     * B1: Tạo key để sinh ra token
     *     SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
     *     String key = Encoders.BASE64.encode(secretKey.getEncoded());
     *     System.out.println(key);
     * B2: Dùng keys mới tạo để sinh ra token

     * */

    public String generateToken(String data) {
//        System.out.println(secretKey);
//        Lấy secretkey đã tạo trước đó sử dụng.
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
//        Dùng key để tạo ra token
        String token = Jwts.builder().setSubject(data).signWith(key).compact();
        return token;
    }

    public String validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // chuẩn bị chìa khóa để tiến hành giải mã
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token) // Truyền token cần giải mã.
                .getBody().getSubject();

    }

}
