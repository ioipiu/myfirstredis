package cn.kgc.tangcco.myfirstredis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-04 20:16
 * @version: V1.0
 **/
@Configuration
public class RedisClusterConfig extends CachingConfigurerSupport {

    @Autowired
    private ClusterConfigurationProperties configurationProperties;

    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(configurationProperties.getMaxIdle());
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(configurationProperties.getMaxTotal());
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(configurationProperties.getNumTestsPerEvictionRun());
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(configurationProperties.isTestOnBorrow());
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(configurationProperties.isTestOnBorrow());
        return jedisPoolConfig;
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        System.out.println(configurationProperties.getClusterNodes());
        System.out.println(configurationProperties.getPassword());
        String[] serverArray = configurationProperties.getClusterNodes().split(",");
        Set<RedisNode> nodes = new HashSet<RedisNode>();
        for(String ipPort:serverArray){
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(configurationProperties.getMaxRedirects());
        redisClusterConfiguration.setPassword(RedisPassword.of(configurationProperties.getPassword()));
        return new JedisConnectionFactory(redisClusterConfiguration,getJedisPoolConfig());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
