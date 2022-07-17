package com.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.mall.product.feign")
@EnableDiscoveryClient
@EnableCaching
@MapperScan("com.mall.product.dao")
@SpringBootApplication
public class MallProductApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallProductApplication.class, args);
  }

}
