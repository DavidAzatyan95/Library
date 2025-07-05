package com.example.springcourse.AuthorsAndBooks.repositories;

import com.example.springcourse.AuthorsAndBooks.dto.StatisticsRedis;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class StatisticsRedisManualRepository {

    private final String KEY_PREFIX = "statistics:";
    private final RedisTemplate<String, Object> redisTemplate;

    public StatisticsRedisManualRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveWithCustomKey(long key, StatisticsRedis stat) {
        String redisKey = KEY_PREFIX + key;
        redisTemplate.opsForValue().set(redisKey, stat);
    }

    public Optional<StatisticsRedis> getByCustomKey(long key) {
        String redisKey = KEY_PREFIX + key;
        StatisticsRedis value = (StatisticsRedis) redisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(value);
    }

    public void deleteByCustomKey(String key) {
        redisTemplate.delete(key);
    }
}
