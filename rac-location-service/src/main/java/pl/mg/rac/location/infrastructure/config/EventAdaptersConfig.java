package pl.mg.rac.location.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.application.port.out.LocationEventPublisher;

@Configuration
public class EventAdaptersConfig {

    private final CarLocationDatabase carLocationDatabase;
    private final LocationEventPublisher eventPublisher;

    public EventAdaptersConfig(CarLocationDatabase carLocationDatabase, LocationEventPublisher eventPublisher) {
        this.carLocationDatabase = carLocationDatabase;
        this.eventPublisher = eventPublisher;
    }
/*

    @Bean
    @EventTypeQualifier(EventType.RAC_RENT_CONFIRMATION)
    public EventAdapter<RacEvent<?>> rentAcceptedEventAdapter() {
        return new RentAcceptedEventAdapter(rentDatabase);
    }
*/


}
