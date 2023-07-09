package com.ahut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ahut.mapper")
public class SecurityUserTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityUserTestApplication.class, args);
    }

}
