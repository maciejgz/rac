package pl.mg.rac.location.infrastructure.out.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.location.application.port.out.LocationEventPublisher;

import java.util.UUID;

@Slf4j
public class LocationKafkaEventPublisher implements LocationEventPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishRentEvent(RacEvent<?> racEvent) {
        log.info("Publishing rent event: {}", racEvent);
        kafkaTemplate.send(KafkaTopicConfig.RAC_RENT_TOPIC, UUID.randomUUID().toString(), racEvent);
    }
}
