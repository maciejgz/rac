package pl.mg.rac.rent.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.application.service.event.*;
import pl.mg.rac.rent.infrastructure.out.messaging.RentKafkaEventPublisher;
import pl.mg.rac.rent.infrastructure.out.persistence.RentJpaRepository;
import pl.mg.rac.rent.infrastructure.out.persistence.RentRepository;

@Configuration
public class EventAdaptersConfig {

    //FIXME get rid of circular dependency
    private final RentDatabase rentDatabase;
    private final RentEventPublisher eventPublisher;

    private final RentJpaRepository rentJpaRepository;

    public EventAdaptersConfig(RentDatabase rentDatabase, RentEventPublisher eventPublisher, RentJpaRepository rentJpaRepository) {
        this.rentDatabase = rentDatabase;
        this.eventPublisher = eventPublisher;
        this.rentJpaRepository = rentJpaRepository;
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentAcceptedEventAdapter() {
        return new RentAcceptedEventAdapter(rentDatabase);
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentFailedCarAdapter() {
        return new RentFailedCarEventAdapter(rentDatabase);
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentFailedUserAdapter() {
        return new RentFailedUserEventAdapter(rentDatabase);
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnAcceptedEventAdapter() {
        return new ReturnAcceptedEventAdapter(rentDatabase);
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnFailedCarAdapter() {
        return new ReturnFailedCarEventAdapter(rentDatabase);
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnFailedUserAdapter() {
        return new ReturnFailedUserEventAdapter(rentDatabase);
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnFailedLocationAdapter() {
        return new ReturnFailedLocationEventAdapter(rentDatabase);
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
