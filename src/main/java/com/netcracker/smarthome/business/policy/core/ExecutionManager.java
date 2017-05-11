package com.netcracker.smarthome.business.policy.core;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ExecutionManager {
    private ExecutorService executor;

    public ExecutionManager() {
        this.executor = Executors.newFixedThreadPool(500);
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    @PreDestroy
    private void stop() {
        executor.shutdown();
    }
}
