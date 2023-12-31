package pl.mg.rac.user.infrastructure.out.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedUserEvent;
import pl.mg.rac.commons.event.rent.RentRequestCarEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedUserEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestCarEvent;
import pl.mg.rac.commons.event.user.UserChargedEvent;
import pl.mg.rac.commons.event.user.UserCreatedEvent;
import pl.mg.rac.commons.event.user.UserDeletedEvent;
import pl.mg.rac.user.application.port.out.UserEventPublisher;

import java.util.UUID;

@Slf4j
public class UserKafkaEventPublisher implements UserEventPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishUserEvent(RacEvent<?> event) {
        log.debug("publishUserCreatedEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_USER_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishUserCreatedEvent(UserCreatedEvent event) {
        log.debug("publishUserCreatedEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_USER_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishUserDeletedEvent(UserDeletedEvent event) {
        log.debug("publishUserDeletedEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_USER_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishUserChargedEvent(UserChargedEvent event) {
        log.debug("publishUserChargedEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_USER_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishRentRequestCarEvent(RentRequestCarEvent event) {
        log.debug("publishRentRequestCarEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_RENT_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishRentFailedUserEvent(RentFailedUserEvent event) {
        log.debug("RentFailedUserEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_RENT_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishReturnRequestCarEvent(ReturnRequestCarEvent event) {
        log.debug("publishReturnRequestCarEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_RENT_TOPIC, UUID.randomUUID().toString(), event);
    }

    @Override
    public void publishReturnFailedUserEvent(ReturnFailedUserEvent event) {
        log.debug("publishReturnFailedUserEvent");
        kafkaTemplate.send(KafkaTopicConfig.RAC_RENT_TOPIC, UUID.randomUUID().toString(), event);
    }
}
