package com.example.eventms.common.service;

public interface RedisService {
    void set(String key, String value);

    String get(String key);

    boolean expire(String key, long expire);

    Boolean del(String key);

    Long incr(String key, long delta);
}
