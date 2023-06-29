package com.cybersoft.cozastore.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {

    @RabbitListener(queues = "test queue01")
    public void listenerQueue(String message) {

        System.out.println("Đây là nội dung lấy từ queue message: " + message);

    }
}
