package com.cybersoft.cozastore.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    // Tao queue
    @Bean
    public Queue queue() {
        return new Queue("test queue01");
    }

    // Tao exchange
    @Bean
    DirectExchange exchange() {
        return new DirectExchange("test exchange01");
    }

    @Bean
    public Binding binding(Queue queue,DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }


}
