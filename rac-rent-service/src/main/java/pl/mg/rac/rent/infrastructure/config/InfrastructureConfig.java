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
import pl.mg.rac.rent.application.service.event.*;
import pl.mg.rac.rent.domain.service.RentDomainService;
import pl.mg.rac.rent.infrastructure.out.messaging.RentKafkaEventPublisher;
import pl.mg.rac.rent.infrastructure.out.persistence.RentJpaRepository;
import pl.mg.rac.rent.infrastructure.out.persistence.RentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final RentJpaRepository rentJpaRepository;

    public InfrastructureConfig( RentJpaRepository rentJpaRepository) {
        this.rentJpaRepository = rentJpaRepository;
    }

    //facade
    @Bean
    public RentFacade rentFacade(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher, EventApplicationService eventApplicationService) {
        return new RentFacade(rentApplicationService(rentDatabase, rentEventPublisher), rentApplicationService(rentDatabase, rentEventPublisher),
                rentApplicationService(rentDatabase, rentEventPublisher), eventApplicationService);
    }

    //application services
    @Bean
    public RentApplicationService rentApplicationService(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher) {
        return new RentApplicationService(rentDatabase, rentEventPublisher);
    }

    @Lazy
    @Bean
    public EventApplicationService eventApplicationService(Map<String, EventAdapter<RacEvent<?>>> eventAdapters) {
        return new EventApplicationService(eventAdapters);
    }

    //domain service
    @Bean
    public RentDomainService rentDomainService() {
        return new RentDomainService();
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



    @Bean
    public EventAdapter<RacEvent<?>> rentAcceptedEventAdapter() {
        return new RentAcceptedEventAdapter(rentDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentFailedCarAdapter() {
        return new RentFailedCarEventAdapter(rentDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentFailedUserAdapter() {
        return new RentFailedUserEventAdapter(rentDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnAcceptedEventAdapter() {
        return new ReturnAcceptedEventAdapter(rentDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnFailedCarAdapter() {
        return new ReturnFailedCarEventAdapter(rentDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnFailedUserAdapter() {
        return new ReturnFailedUserEventAdapter(rentDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnFailedLocationAdapter() {
        return new ReturnFailedLocationEventAdapter(rentDatabase());
    }


}
