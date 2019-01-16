package com.example.myproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SuppressWarnings("deprecation")
@SpringBootApplication
@MapperScan("com.example.myproject.dao")
public class MyprojectApplication {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(MyprojectApplication.class);
//    }
    public static void main(String[] args) {
        SpringApplication.run(MyprojectApplication.class, args);
    }

}

