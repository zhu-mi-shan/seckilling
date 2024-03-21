package com.example.rbac_back.Utils.RabbitMQ;

import com.example.rbac_back.POJO.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        String context = message + new Date();
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend("hello", context);
    }

    public void sendOrder(String order){
        rabbitTemplate.convertAndSend("order", order);
    }

    public void sendOrderCancel(int ticket_id) {rabbitTemplate.convertAndSend("orderCancel",ticket_id);}
}
