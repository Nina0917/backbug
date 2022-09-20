package com.yuanning.backbug;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 插入缓存数据
     */
    @Test
    public void set() {
        stringRedisTemplate.opsForValue().set("redis_key", "redis_vale");
    }

    /**
     * 读取缓存数据
     */
    @Test
    public void get() {
        String value = stringRedisTemplate.opsForValue().get("redis_key");
        System.out.println(value);
    }

}
