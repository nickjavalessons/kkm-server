package com.kkm.server.cash.worker;

import com.kkm.server.repository.KkmServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@ConfigurationProperties(prefix = "worker.config")
@Configuration
public class WorkerThreadConfig {
    private int corePoolSize;
    private int maxPoolSize;
    private String threadNamePrefix;

    @Autowired
    KkmServerRepository kkmServerRepository;


    @Bean
    @Primary
    public TaskExecutor workerThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);

        maxPoolSize = (int)kkmServerRepository.count();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public KkmServerRepository getKkmServerRepository() {
        return kkmServerRepository;
    }

    public void setKkmServerRepository(KkmServerRepository kkmServerRepository) {
        this.kkmServerRepository = kkmServerRepository;
    }
}
