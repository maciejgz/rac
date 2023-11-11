package pl.mg.rac.car.infrastructure.out.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String RAC_CAR_TOPIC = "rac-car";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public NewTopic userRegisteredConfig() {
        return TopicBuilder.name(RAC_CAR_TOPIC)
                .partitions(3)
                .replicas(1)
                .compact()
                .build();
    }
}
