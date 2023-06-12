package com.example.novel_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCaching
@MapperScan("com.example.novel_backend.dao.mapper")
public class NovelBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelBackendApplication.class, args);
    }

}
