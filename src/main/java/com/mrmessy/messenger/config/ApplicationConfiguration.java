package com.mrmessy.messenger.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement(proxyTargetClass = true)
public class ApplicationConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .build();
    }

    @Bean(name = "asynchronousExecutor")
    public Executor createAsynchronousListenerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.initialize();
        return executor;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
