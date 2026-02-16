package com.spring.Journal.scheduler;

import com.spring.Journal.Config.AppCacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    private final AppCacheConfig appCacheConfig;

    public JobScheduler(AppCacheConfig appCacheConfig) {
        this.appCacheConfig = appCacheConfig;
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void autoRefreshConfig() {
        logger.info("Refreshing App Cache at {}", LocalDateTime.now());
        appCacheConfig.init();
    }

    @Scheduled(cron = "0 * * ? * *")
    public void hello() {
        logger.info("Hello, Time right now is: {}", LocalDateTime.now());
    }
}
