package com.example.rbac_back.Utils.RabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Bean
    public Queue order(){return new Queue("order");}

    @Bean
    public Queue orderCancel(){return new Queue("orderCancel");}
}
