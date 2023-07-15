package com.atguigu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.atguigu.educenter.mapper")
@ComponentScan({"com.atguigu"})
public class UcenterApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(UcenterApplication.class, args);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

}
