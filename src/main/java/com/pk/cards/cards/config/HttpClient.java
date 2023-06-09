package com.pk.cards.cards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClient {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


}
