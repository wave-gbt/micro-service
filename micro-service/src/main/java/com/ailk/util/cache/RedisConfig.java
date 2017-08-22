package com.ailk.util.cache;

import org.n3r.diamond.client.Miner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Created by zhangxy on 2017/7/26.
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name="jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(2000);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMaxWaitMillis(5000);
        return jedisPoolConfig;
    }


    @Bean(name="cacheRedisConnFactory")
    public JedisConnectionFactory redisConnFactory(@Qualifier("jedisPoolConfig") JedisPoolConfig jpc) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

        // 配置diamond取值
        String cacheRedisURL = new Miner().getMiner("RedisData").getString("singleRedisAddr");

        jedisConnectionFactory.setHostName(cacheRedisURL.split(":")[0]);
        jedisConnectionFactory.setPort(Integer.valueOf(cacheRedisURL.split(":")[1]));
        jedisConnectionFactory.setPoolConfig(jpc);
        return jedisConnectionFactory;
    }



    @Bean(name="cacheRedisTemplate")
    public RedisTemplate<String, String> redisTemplate(@Qualifier("cacheRedisConnFactory") RedisConnectionFactory cf){
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean(name="cacheRedisUtil")
    public RedisOptService redisUtil(@Qualifier("cacheRedisConnFactory") RedisConnectionFactory cf) {
        RedisOptService redisUtil = new RedisOptService();
        redisUtil.setConnectionFactory(cf);
        return redisUtil;
    }
}
