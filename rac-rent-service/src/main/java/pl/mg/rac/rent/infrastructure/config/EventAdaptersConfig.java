package pl.mg.rac.rent.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.service.event.*;

@Configuration
public class EventAdaptersConfig {

    @Bean
    @EventTypeQualifier(EventType.RAC_RENT_CONFIRMATION)
    public EventAdapter<RacEvent<?>> rentAcceptedEventAdapter() {
        return new RentAcceptedEventAdapter();
    }

    @Bean
    @EventTypeQualifier(EventType.RAC_RENT_FAILED_CAR)
    public EventAdapter<RacEvent<?>> rentFailedCarAdapter() {
        return new RentFailedCarEventAdapter();
    }

    @Bean
    @EventTypeQualifier(EventType.RAC_RENT_FAILED_USER)
    public EventAdapter<RacEvent<?>> rentFailedUserAdapter() {
        return new RentFailedUserEventAdapter();
    }

    @Bean
    @EventTypeQualifier(EventType.RAC_RETURN_CONFIRMATION)
    public EventAdapter<RacEvent<?>> returnAcceptedEventAdapter() {
        return new ReturnAcceptedEventAdapter();
    }

    @Bean
    @EventTypeQualifier(EventType.RAC_RETURN_FAILED_CAR)
    public EventAdapter<RacEvent<?>> returnFailedCarAdapter() {
        return new ReturnFailedCarEventAdapter();
    }

    @Bean
    @EventTypeQualifier(EventType.RAC_RETURN_FAILED_USER)
    public EventAdapter<RacEvent<?>> returnFailedUserAdapter() {
        return new ReturnFailedUserEventAdapter();
    }

    @Bean
    @EventTypeQualifier(EventType.RAC_RETURN_FAILED_LOCATION)
    public EventAdapter<RacEvent<?>> returnFailedLocationAdapter() {
        return new ReturnFailedLocationEventAdapter();
    }


}
