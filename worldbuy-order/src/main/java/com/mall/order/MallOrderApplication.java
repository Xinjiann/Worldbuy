package com.mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@EnableRedisHttpSession
@EnableRabbit
@MapperScan("com.mall.order.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class MallOrderApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallOrderApplication.class, args);
  }

}
