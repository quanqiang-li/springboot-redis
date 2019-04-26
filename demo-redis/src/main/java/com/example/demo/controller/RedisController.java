package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RedisService;

@RestController
@RequestMapping("redis")
public class RedisController {

	@Resource(name = "strRedisTemplate")
	private RedisTemplate<String, String> template;

//	 @Autowired
//	 private RedisTemplate template;
	/*
	 * 上面@Autowired的RedisTemplate
	 * template会启动报错,因为有两个方法可以创建这个bean需要修改template属性为方法名strRedisTemplate
	 * 即private RedisTemplate strRedisTemplate;
	 * 或者使用@Primary <br>
	 * 
	 * Field template1 in
	 * com.example.demo.controller.RedisController required a single bean, but 2
	 * were found: - strRedisTemplate: defined by method 'strRedisTemplate' in class
	 * path resource [com/example/demo/conf/RedisConfig.class] -
	 * stringRedisTemplate: defined by method 'stringRedisTemplate' in class path
	 * resource
	 * [org/springframework/boot/autoconfigure/data/redis/RedisAutoConfiguration.
	 * class]
	 * 
	 */

	@Autowired
	private RedisService redisService;

	@GetMapping("setAndGet")
	public String setAndGet(String key, String value) {
		System.out.println(template);
		template.opsForValue().set(key, value);
		return (String) template.opsForValue().get(key);
	}

	@GetMapping("test")
	public String test(String key,String value) {
		redisService.set(key, value);
		return redisService.get(key) + "";
	}

}
