package ru.spb.itmo.asashina.tconnector.processor;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.springframework.stereotype.Component;
import ru.spb.itmo.asashina.tconnector.model.message.CategoryStatMessage;
import ru.spb.itmo.asashina.tconnector.model.message.KafkaTransactionMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class FlinkKafkaCategoryStatsProcessor {

    private final KafkaSource<KafkaTransactionMessage> kafkaCategoryStatsSource;
    private final KafkaSink<CategoryStatMessage> kafkaCategoryStatsSink;

    public FlinkKafkaCategoryStatsProcessor(
            KafkaSource<KafkaTransactionMessage> kafkaCategoryStatsSource,
            KafkaSink<CategoryStatMessage> kafkaCategoryStatsSink) {

        this.kafkaCategoryStatsSource = kafkaCategoryStatsSource;
        this.kafkaCategoryStatsSink = kafkaCategoryStatsSink;
    }

    public void process() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var stream = env.fromSource(
                        kafkaCategoryStatsSource, WatermarkStrategy.noWatermarks(), "Kafka Source")
                .setParallelism(3)
                .keyBy(KafkaTransactionMessage::getPaymentCategory)
                .window(SlidingProcessingTimeWindows.of(Time.seconds(30), Time.seconds(5)))
                .process(new CategoryStatsFlinkWindowFunction())
                .sinkTo(kafkaCategoryStatsSink);
        env.execute();
    }

    public static class CategoryStatsFlinkWindowFunction
            extends ProcessWindowFunction<KafkaTransactionMessage, CategoryStatMessage, String, TimeWindow> {

        @Override
        public void process(
                String s,
                Context context,
                Iterable<KafkaTransactionMessage> elements,
                Collector<CategoryStatMessage> out) {

            Set<UUID> ids = new HashSet<>();
            long count = 0;
            BigDecimal maxAmount = new BigDecimal(0);
            BigDecimal minAmount = BigDecimal.valueOf(Long.MAX_VALUE);
            BigDecimal sumAmount = new BigDecimal(0);
            for (var transaction : elements) {
                if (ids.contains(transaction.getId())) {
                    continue;
                }
                ids.add(transaction.getId());
                var amount = transaction.getAmount();
                sumAmount = sumAmount.add(amount);
                if (amount.compareTo(minAmount) < 0) {
                    minAmount = amount;
                }
                if (amount.compareTo(maxAmount) > 0) {
                    maxAmount = amount;
                }
                count++;
            }

            out.collect(
                    new CategoryStatMessage()
                            .setCount(count)
                            .setCategory(s)
                            .setMaxAmount(maxAmount)
                            .setMinAmount(minAmount)
                            .setTimestamp(System.currentTimeMillis())
                            .setAverageAmount(
                                    sumAmount.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP)));
        }

    }

}
