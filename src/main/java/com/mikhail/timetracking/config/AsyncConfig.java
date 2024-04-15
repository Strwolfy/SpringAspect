package com.mikhail.timetracking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // Основное количество потоков
        executor.setMaxPoolSize(5); // Максимальное количество потоков
        executor.setQueueCapacity(100); // Емкость очереди
        executor.setThreadNamePrefix("Async-"); // Префикс имени потока
        executor.initialize();
        return executor;
    }
}
