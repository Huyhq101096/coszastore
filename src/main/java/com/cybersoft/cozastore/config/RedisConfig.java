package com.cybersoft.cozastore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    // Khai báo thông tin kết nối tới server redis

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);

        return new LettuceConnectionFactory(configuration);
    }

    // Redis hỗ trợ lưu dữ liệu theo kiểu key,value . lưu theo kiểu <String,String>
    // Sử dụng String thì ngôn ngữ nào cũng lấy được
    // or <String,Object>
    @Bean
    @Primary
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory connectionFactory) {
        // Redistemplate : hỗ trợ lưu trữ và truy xuất dữ liệu lưu trữ trên server redis.
        RedisTemplate<String, String> template = new RedisTemplate<>();
        // Khai báo thông tin server Redis sẽ lưu trữ dữ liệu cho RedisTemplate
        template.setConnectionFactory(connectionFactory);
        // chỉ lưu tene của cache redis mà mình đặt tên.
        template.setKeySerializer(new StringRedisSerializer());
        return template;

    }

}
