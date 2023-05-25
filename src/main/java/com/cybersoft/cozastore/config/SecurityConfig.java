package com.cybersoft.cozastore.config;

import com.cybersoft.cozastore.filter.JWTFilter;
import com.cybersoft.cozastore.provider.CustomAuthenManagerProvider;
import com.cybersoft.cozastore.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenManagerProvider customAuthenManagerProvider;

    @Autowired
    private JWTFilter jwtFilter;
    //MD5, SHA1, RSA...
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Tạo ra Authentication để custom lai thông tin chứng thực
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
//      CustomUserDetailService userDetailService = new CustomUserDetailService();
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailService)
//                .passwordEncoder(passwordEncoder())
                .authenticationProvider(customAuthenManagerProvider)
                .build();
    }

    // custom lại thông tin cấu hình của security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                // không sử dụng session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/home/**").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .and().httpBasic()
                .build();
    }


}
