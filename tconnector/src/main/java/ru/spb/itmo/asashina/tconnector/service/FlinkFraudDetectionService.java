package ru.spb.itmo.asashina.tconnector.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import ru.spb.itmo.asashina.tconnector.processor.FlinkKafkaFraudDetectionProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class FlinkFraudDetectionService {

    private final FlinkKafkaFraudDetectionProcessor processor;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public FlinkFraudDetectionService(FlinkKafkaFraudDetectionProcessor processor) {
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
