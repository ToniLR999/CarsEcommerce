package com.tonilr.CarsEcommerce.Services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PRODUCT_CACHE_PREFIX = "product:";
    private static final long CACHE_TIMEOUT = 60; // 60 minutos

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(value = "products", key = "#id")
    public Object getProduct(Long id) {
        String key = PRODUCT_CACHE_PREFIX + id;
        return redisTemplate.opsForValue().get(key);
    }

    public void saveProduct(Long id, Object product) {
        String key = PRODUCT_CACHE_PREFIX + id;
        redisTemplate.opsForValue().set(key, product, CACHE_TIMEOUT, TimeUnit.MINUTES);
    }

    @CacheEvict(value = "products", key = "#id")
    public void removeProduct(Long id) {
        String key = PRODUCT_CACHE_PREFIX + id;
        redisTemplate.delete(key);
    }

    public void clearAllProducts() {
        redisTemplate.delete(redisTemplate.keys(PRODUCT_CACHE_PREFIX + "*"));
    }

    public void extendProductCache(Long id) {
        String key = PRODUCT_CACHE_PREFIX + id;
        redisTemplate.expire(key, CACHE_TIMEOUT, TimeUnit.MINUTES);
    }
} 