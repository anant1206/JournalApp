package com.spring.Journal.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
// start redis server
//sudo service redis-server start
//
//redis-cli

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisService(RedisTemplate<String, String> redisTemplate,
                        ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if (o == null) return null;
            return objectMapper.readValue(o.toString(), entityClass);
        } catch (Exception e) {
            log.error("Redis GET error for key: {}", key, e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttlSeconds) {
        try {
            String jsonValue = objectMapper.writeValueAsString(o);
            log.info(jsonValue);
            redisTemplate.opsForValue()
                    .set(key, jsonValue, ttlSeconds, TimeUnit.SECONDS);
            log.info("Added in Redis Cache: "+ key.substring(6));
        } catch (Exception e) {
            log.error("Redis SET error for key: {}", key, e);
        }
    }
}

