package ru.spb.itmo.asashina.tconnector.processor;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.spb.itmo.asashina.tconnector.model.message.FraudDetectionResultMessage;
import ru.spb.itmo.asashina.tconnector.model.message.KafkaTransactionMessage;
import ru.spb.itmo.asashina.tconnector.model.request.FraudDetectionApiRequest;
import ru.spb.itmo.asashina.tconnector.model.response.FraudDetectionApiResponse;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class FlinkKafkaFraudDetectionProcessor {

    private static final RestClient REST_CLIENT = RestClient.create();
    private static final Logger log = Logger.getLogger(FlinkKafkaFraudDetectionProcessor.class.getName());

    private final KafkaSource<KafkaTransactionMessage> kafkaFraudDetectionSource;
    private final KafkaSink<FraudDetectionResultMessage> kafkaFraudDetectionSink;

    @Value("${fraud-detection.url}")
    private String fraudDetectionApiUrl;

    public FlinkKafkaFraudDetectionProcessor(
            KafkaSource<KafkaTransactionMessage> kafkaFraudDetectionSource,
            KafkaSink<FraudDetectionResultMessage> kafkaFraudDetectionSink) {

        this.kafkaFraudDetectionSource = kafkaFraudDetectionSource;
        this.kafkaFraudDetectionSink = kafkaFraudDetectionSink;
    }

    public void process() throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var stream = env.fromSource(
                    kafkaFraudDetectionSource, WatermarkStrategy.noWatermarks(), "Kafka Source")
                .setParallelism(3)
                .keyBy(KafkaTransactionMessage::getId)
                .process(new FraudDetectionFlinkWindowFunction(fraudDetectionApiUrl))
                .sinkTo(kafkaFraudDetectionSink);
        env.execute();
    }

    private static class FraudDetectionFlinkWindowFunction
            extends ProcessFunction<KafkaTransactionMessage, FraudDetectionResultMessage> {

        private final String apiUrl;

        public FraudDetectionFlinkWindowFunction(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        @Override
        public void processElement(
                KafkaTransactionMessage value,
                Context ctx,
                Collector<FraudDetectionResultMessage> out) throws Exception {

            try {
                var response = REST_CLIENT.post()
                        .uri(apiUrl + "/predict")
                        .contentType(APPLICATION_JSON)
                        .body(mapToRequest(value))
                        .retrieve()
                        .toEntity(FraudDetectionApiResponse.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException(
                            "Response code" + response.getStatusCode() + " with body %s" + response.getBody());
                }
                out.collect(
                        new FraudDetectionResultMessage()
                                .setId(value.getId())
                                .setResult(response.getBody().getResult())
                                .setTimestamp(System.currentTimeMillis()));
            } catch (Exception e) {
                log.log(SEVERE, "Something went wrong: " + e.getMessage(), e);
                out.collect(
                        new FraudDetectionResultMessage()
                                .setId(value.getId())
                                .setResult("ERROR")
                                .setTimestamp(System.currentTimeMillis()));
            }
        }

        private FraudDetectionApiRequest mapToRequest(KafkaTransactionMessage message) {
            return new FraudDetectionApiRequest()
                    .setType(List.of(message.getType()))
                    .setAmount(List.of(message.getAmount()))
                    .setOriginOldBalance(List.of(message.getOriginOldBalance()))
                    .setOriginNewBalance(List.of(message.getOriginNewBalance()))
                    .setDestinationOldBalance(List.of(message.getDestinationOldBalance()))
                    .setDestinationNewBalance(List.of(message.getDestinationNewBalance()));
        }

    }

}
