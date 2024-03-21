package com.example.rbac_back.Controller;

import com.example.rbac_back.Utils.RabbitMQ.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private RabbitMQSender sender;

    @PostMapping
    public void sendMessage(@RequestBody String message) {
        sender.send(message);
    }

    @GetMapping("/demo")
    public String sendMessageTest() {
        sender.send("hello world");
        return "hello";
    }
}

