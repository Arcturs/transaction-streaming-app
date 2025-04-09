package ru.spb.itmo.asashina.tconnector.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import ru.spb.itmo.asashina.tconnector.processor.FlinkKafkaCategoryStatsProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class FlinkKafkaCategoryStatsService {

    private final FlinkKafkaCategoryStatsProcessor processor;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public FlinkKafkaCategoryStatsService(FlinkKafkaCategoryStatsProcessor processor) {
        this.processor = processor;
    }

    @PostConstruct
    public void execute() {
        executorService.execute(() -> {
            try {
                processor.process();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}
