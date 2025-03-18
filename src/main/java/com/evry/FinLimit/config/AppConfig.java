package com.evry.FinLimit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

//    public AppConfig() {
//        System.out.println("🔥 AppConfig загружен!");
//    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
