package com.xxxx.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Mr.Z
 * @title: RedisConfig
 * @projectName yeb
 * @description: TODO
 * @date 2022/5/1315:41
 */
@SuppressWarnings({"all"})
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object>redisTemplate=new RedisTemplate<>();
        //String 类型   key序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //String 类型   Value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //has 类型   key序列器

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //has 类型   Value序列器

        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
