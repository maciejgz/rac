package pl.mg.rac.rent.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.application.service.event.*;

@Configuration
public class EventAdaptersConfig {

    private final RentDatabase rentDatabase;
    private final RentEventPublisher eventPublisher;

    public EventAdaptersConfig(RentDatabase rentDatabase, RentEventPublisher eventPublisher) {
        this.rentDatabase = rentDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Bean
    @EventTypeQualifier(EventType.RAC_RENT_CONFIRMATION)
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

}
