package com.example.timeout.server.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

  static ThreadPoolTaskExecutor executor;

  static Map<String, ThreadPoolTaskExecutor> executorMap = new HashMap<>();

  public AsyncTaskExecutor taskExecutor(String str) {

    if (executorMap.get(str) != null) {
      return executorMap.get((str));
    } else {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(3);
      executor.setQueueCapacity(0);
      executor.setMaxPoolSize(3);
      executor.setThreadNamePrefix(str + "-");
      executor.initialize();
      executorMap.put(str, executor);
      return executor;
    }

  }

}
