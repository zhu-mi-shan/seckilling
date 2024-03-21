package com.example.rbac_back;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
public class HelloTests {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void hello(){
        redisTemplate.opsForValue().set("tokee", "w2w2");
    }
}
