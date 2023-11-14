package pl.mg.rac.rent.infrastructure.in.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.facade.RentFacade;

import static pl.mg.rac.rent.infrastructure.out.messaging.KafkaTopicConfig.RAC_RENT_TOPIC;

@Component
@Slf4j
public class RentKafkaEventListener {

    protected static final String RAC_RENT_SERVICE_GROUP_ID = "rac-rent-service";

    private final RentFacade rentFacade;

    public RentKafkaEventListener(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @KafkaListener(topics = RAC_RENT_TOPIC, groupId = RAC_RENT_SERVICE_GROUP_ID)
    public void listen(ConsumerRecord<String, RacEvent<?>> record) {
        log.debug("listen() called with: record = [" + record + "]");
        rentFacade.handleIncomingEvent(record.value());
    }
}
