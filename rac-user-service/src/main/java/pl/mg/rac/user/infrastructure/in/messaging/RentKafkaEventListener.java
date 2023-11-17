package pl.mg.rac.user.infrastructure.in.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.user.application.facade.UserFacade;

import static pl.mg.rac.user.infrastructure.out.messaging.KafkaTopicConfig.RAC_RENT_TOPIC;

@Component
@Slf4j
public class RentKafkaEventListener {

    protected static final String RAC_USER_SERVICE_GROUP_ID = "rac-user-service";

    private final UserFacade userFacade;

    public RentKafkaEventListener(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @KafkaListener(topics = RAC_RENT_TOPIC, groupId = RAC_USER_SERVICE_GROUP_ID)
    public void listen(ConsumerRecord<String, RacEvent<?>> record) {
        log.debug("listen() called with: record = [" + record + "]");
        userFacade.handleIncomingEvent(record.value());
    }

}
