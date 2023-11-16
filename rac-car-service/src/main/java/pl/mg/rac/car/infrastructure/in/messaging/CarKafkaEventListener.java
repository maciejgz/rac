package pl.mg.rac.car.infrastructure.in.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.mg.rac.car.application.facade.CarFacade;
import pl.mg.rac.commons.event.RacEvent;

import static pl.mg.rac.car.infrastructure.out.messaging.KafkaTopicConfig.RAC_RENT_TOPIC;

@Component
@Slf4j
public class CarKafkaEventListener {

    protected static final String RAC_CAR_SERVICE_GROUP_ID = "rac-car-service";

    private final CarFacade carFacade;

    public CarKafkaEventListener(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    @KafkaListener(topics = RAC_RENT_TOPIC, groupId = RAC_CAR_SERVICE_GROUP_ID)
    public void listen(ConsumerRecord<String, RacEvent<?>> record) {
        log.debug("listen() called with: record = [" + record + "]");
        carFacade.handleIncomingEvent(record.value());
    }
}
