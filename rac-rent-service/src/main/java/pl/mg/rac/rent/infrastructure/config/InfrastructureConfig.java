package pl.mg.rac.rent.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentConfirmationEvent;
import pl.mg.rac.rent.application.facade.RentFacade;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.application.service.EventApplicationService;
import pl.mg.rac.rent.application.service.RentApplicationService;
import pl.mg.rac.rent.application.service.event.EventAdapter;
import pl.mg.rac.rent.application.service.event.RentAcceptedEventAdapter;
import pl.mg.rac.rent.domain.service.RentDomainService;
import pl.mg.rac.rent.infrastructure.out.messaging.RentKafkaEventPublisher;
import pl.mg.rac.rent.infrastructure.out.persistence.RentJpaRepository;
import pl.mg.rac.rent.infrastructure.out.persistence.RentRepository;

import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final RentJpaRepository rentJpaRepository;
    private final Map<String, EventAdapter<RacEvent<?>>> eventAdapters;

    public InfrastructureConfig(RentJpaRepository rentJpaRepository, Map<String, EventAdapter<RacEvent<?>>> eventAdapters) {
        this.rentJpaRepository = rentJpaRepository;
        this.eventAdapters = eventAdapters;
    }

    //facade
    @Bean
    public RentFacade rentFacade() {
        return new RentFacade(rentApplicationService(), rentApplicationService(), rentApplicationService(), eventApplicationService());
    }

    //application services
    @Bean
    public RentApplicationService rentApplicationService() {
        return new RentApplicationService(rentDatabase(), rentEventPublisher());
    }

    @Bean
    public EventApplicationService eventApplicationService() {
        return new EventApplicationService(eventAdapters);
    }

    //domain service
    public RentDomainService rentDomainService() {
        return new RentDomainService();
    }

    // event adapters
    //TODO add event adapters here
    @Bean
    @Qualifier
    public EventAdapter<RentConfirmationEvent> rentConfirmationEventEventAdapter() {
        return new RentAcceptedEventAdapter();
    }

    //outgoing port adapters
    @Bean
    public RentDatabase rentDatabase() {
        return new RentRepository(rentJpaRepository);
    }

    @Bean
    public RentEventPublisher rentEventPublisher() {
        return new RentKafkaEventPublisher();
    }

}
