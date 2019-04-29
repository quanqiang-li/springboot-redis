package com.example.demo.conf;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

	/**
	 * spring boot session 存在在redis默认的序列号会出现\xac\xed\x00\x05t\x00,下面的bean替换为jackson的序列化方式可以解决问题
	 * @return
	 */
	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		return new Jackson2JsonRedisSerializer<>(Object.class);
	}

	// 方法名字作为bean交给容器管理
	@Bean
	public RedisTemplate<String, String> strRedisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		return template;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(factory);
		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		/** Jackson序列化 json占用的内存最小 */
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		/** Jdk序列化 JdkSerializationRedisSerializer是最高效的 */
//      JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		/** String序列化 */
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		/** 将key value 进行stringRedisSerializer序列化 */
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		/** 将HashKey HashValue 进行序列化 */
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				this.getRedisCacheConfigurationWithTtl(600), // 默认策略，未配置的 key 会使用这个
				this.getRedisCacheConfigurationMap() // 指定 key 策略
		);
	}

	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		// @CacheConfig(cacheNames = "cityCache") 对应的是下面map的key
		redisCacheConfigurationMap.put("cityCache", this.getRedisCacheConfigurationWithTtl(100));
		redisCacheConfigurationMap.put("UserInfoListAnother", this.getRedisCacheConfigurationWithTtl(18000));

		return redisCacheConfigurationMap;
	}

	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
				.entryTtl(Duration.ofSeconds(seconds));

		return redisCacheConfiguration;
	}
}
