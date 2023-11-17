package pl.mg.rac.car.infrastructure.out.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.commons.event.RacEvent;

import java.util.UUID;

@Slf4j
public class CarKafkaEventPublisher implements CarEventPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishCarEvent(RacEvent<?> event) {
        kafkaTemplate.send(KafkaTopicConfig.RAC_CAR_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishRentEvent(RacEvent<?> event) {
        kafkaTemplate.send(KafkaTopicConfig.RAC_RENT_TOPIC, UUID.randomUUID().toString(), event);
    }
}
