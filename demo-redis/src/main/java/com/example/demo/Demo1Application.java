package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
//扫描的是mapper.java
@MapperScan("com.example.demo.mapper")
@EnableCaching
//开启session共享到redis,超时时间60s,默认1800s
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 180)
public class Demo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

}
