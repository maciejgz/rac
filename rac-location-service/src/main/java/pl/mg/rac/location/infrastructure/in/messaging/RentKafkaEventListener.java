package pl.mg.rac.location.infrastructure.in.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.location.application.facade.LocationFacade;

@Component
@Slf4j
public class RentKafkaEventListener {

    protected static final String RAC_LOCATION_SERVICE_GROUP_ID = "rac-location-service";
    private static final String RAC_RENT_TOPIC = "rac-rent";

    private final LocationFacade locationFacade;

    public RentKafkaEventListener(LocationFacade locationFacade) {
        this.locationFacade = locationFacade;
    }

    @KafkaListener(topics = RAC_RENT_TOPIC, groupId = RAC_LOCATION_SERVICE_GROUP_ID)
    public void listen(ConsumerRecord<String, RacEvent<?>> record) {
        log.debug("listen() called with: record = [" + record + "]");
        locationFacade.handleIncomingEvent(record.value());
    }
}
