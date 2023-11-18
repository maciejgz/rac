package pl.mg.rac.rent.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.facade.RentFacade;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.application.service.EventApplicationService;
import pl.mg.rac.rent.application.service.RentApplicationService;
import pl.mg.rac.rent.application.service.event.EventAdapter;
import pl.mg.rac.rent.domain.service.RentDomainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final Map<String, EventAdapter<RacEvent<?>>> eventAdapters;

    public InfrastructureConfig(List<EventAdapter<RacEvent<?>>> eventAdapterList) {
        this.eventAdapters = new HashMap<>();
        for (EventAdapter<RacEvent<?>> eventAdapter : eventAdapterList) {
            this.eventAdapters.put(eventAdapter.getEventType(), eventAdapter);
        }
    }

    //facade
    @Bean
    public RentFacade rentFacade(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher) {
        return new RentFacade(rentApplicationService(rentDatabase, rentEventPublisher), rentApplicationService(rentDatabase, rentEventPublisher),
                rentApplicationService(rentDatabase, rentEventPublisher), eventApplicationService());
    }

    //application services
    @Bean
    public RentApplicationService rentApplicationService(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher) {
        return new RentApplicationService(rentDatabase, rentEventPublisher);
    }

    @Bean
    public EventApplicationService eventApplicationService() {
        return new EventApplicationService(eventAdapters);
    }

    //domain service
    @Bean
    public RentDomainService rentDomainService() {
        return new RentDomainService();
    }

}
