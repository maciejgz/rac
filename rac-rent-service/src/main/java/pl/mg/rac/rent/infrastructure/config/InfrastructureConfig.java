package pl.mg.rac.rent.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.facade.RentFacade;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.application.service.EventApplicationService;
import pl.mg.rac.rent.application.service.RentApplicationService;
import pl.mg.rac.rent.application.service.event.*;
import pl.mg.rac.rent.application.service.monitor.RentRequestMonitor;
import pl.mg.rac.rent.application.service.monitor.ReturnRequestMonitor;
import pl.mg.rac.rent.domain.service.RentDomainService;
import pl.mg.rac.rent.infrastructure.out.messaging.RentKafkaEventPublisher;
import pl.mg.rac.rent.infrastructure.out.persistence.RentJpaRepository;
import pl.mg.rac.rent.infrastructure.out.persistence.RentRepository;

import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
@EnableScheduling
public class InfrastructureConfig {

    private final RentJpaRepository rentJpaRepository;

    public InfrastructureConfig(RentJpaRepository rentJpaRepository) {
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

    @Bean
    public EventApplicationService eventApplicationService(Map<String, EventAdapter<RacEvent<?>>> eventAdapters) {
        return new EventApplicationService(eventAdapters);
    }

    @Bean
    public RentRequestMonitor rentRequestMonitor(RentDatabase rentDatabase) {
        return new RentRequestMonitor(rentDatabase);
    }

    @Bean
    public ReturnRequestMonitor returnRequestMonitor(RentDatabase rentDatabase) {
        return new ReturnRequestMonitor(rentDatabase);
    }

    @Scheduled(fixedRate = 60000) // Check every 60 seconds
    public void checkRentRequests() {
        rentRequestMonitor(rentDatabase()).checkRentRequests();
    }

    @Scheduled(fixedRate = 60000) // Check every 60 seconds
    public void checkReturnRequests() {
        returnRequestMonitor(rentDatabase()).checkReturnRequests();
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
        return new RentAcceptedEventAdapter(rentDatabase(), rentEventPublisher());
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
        return new ReturnAcceptedEventAdapter(rentDatabase(), rentEventPublisher());
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
