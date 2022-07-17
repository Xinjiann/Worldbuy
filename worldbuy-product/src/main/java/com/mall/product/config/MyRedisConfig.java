package com.mall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: MyRedisConfig</p>
 * Description：
 * date：2022/7/15 12:33
 */
@Configuration
public class MyRedisConfig {

	@Value("${redisIp}")
	private String ipAddr;

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://" + ipAddr + ":6379");
		return Redisson.create(config);
	}
}
