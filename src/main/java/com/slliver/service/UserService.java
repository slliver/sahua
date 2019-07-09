package com.slliver.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/7 10:30
 * @version: 1.0
 */
public class UserService {


    private RedisTemplate redisTemplate;

    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_NAME = "name";

    public void save(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        boolean flagKey = redisTemplate.hasKey(KEY_NAME);
        if(flagKey){
            redisTemplate.delete(KEY_NAME);
            valueOperations.set(KEY_NAME, "zhangsan");
        }else{
            valueOperations.set(KEY_NAME, "zhangsan123");
        }

        ValueOperations<String, String> strValues = stringRedisTemplate.opsForValue();
        strValues.set("name", "lisi");
    }
}
