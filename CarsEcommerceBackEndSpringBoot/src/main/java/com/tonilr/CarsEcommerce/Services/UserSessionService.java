package com.tonilr.CarsEcommerce.Services;

import com.tonilr.CarsEcommerce.Entities.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserSessionService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String USER_SESSION_PREFIX = "user:session:";
    private static final long SESSION_TIMEOUT = 30; // 30 minutos

    public UserSessionService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveUserSession(String token, User user) {
        String key = USER_SESSION_PREFIX + token;
        redisTemplate.opsForValue().set(key, user, SESSION_TIMEOUT, TimeUnit.MINUTES);
    }

    public User getUserSession(String token) {
        String key = USER_SESSION_PREFIX + token;
        return (User) redisTemplate.opsForValue().get(key);
    }

    public void removeUserSession(String token) {
        String key = USER_SESSION_PREFIX + token;
        redisTemplate.delete(key);
    }

    public void extendUserSession(String token) {
        String key = USER_SESSION_PREFIX + token;
        redisTemplate.expire(key, SESSION_TIMEOUT, TimeUnit.MINUTES);
    }
} 