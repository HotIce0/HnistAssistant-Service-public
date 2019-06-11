package com.hotice0.hnist_assistant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.hotice0.hnist_assistant.db.mapper")
public class HnistAssistantApplication {
    public static void main(String[] args) {
        SpringApplication.run(HnistAssistantApplication.class, args);
    }
}


/*
public class HnistAssistantApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HnistAssistantApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(HnistAssistantApplication.class, args);
    }
}
 */