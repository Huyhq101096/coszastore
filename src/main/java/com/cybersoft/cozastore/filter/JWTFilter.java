package com.cybersoft.cozastore.filter;


import com.cybersoft.cozastore.utils.JWTHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// OncePerRequestFilter nếu có request thì chạy vô đây

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTHelperUtils jwtHelperUtils;

    // B1: Lấy token
    //B2: Giải mã token
    //B3: Token hợp lệ tạo chứng thực cho Security
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       // Lấy giá trị trên header
        String header = request.getHeader("Authorization");
        try {
            String token = header.substring(7);
            // Kiểm tra token lấy được xem có thể do hệ thống sinh ra hay không.
            String data = jwtHelperUtils.validateToken(token);

            if(!data.isEmpty()) {
            // Tạo chứng thực cho Security
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken("","", new ArrayList<>());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            // Token không hợp lệ
            System.out.println("token không hợp lệ");
        }
        // Cho phép người dùng đi vào link người dùng muốn truy cập
        filterChain.doFilter(request,response);
    }
}
