package pl.mg.rac.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.user.application.facade.UserFacade;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.application.service.EventApplicationService;
import pl.mg.rac.user.application.service.UserApplicationService;
import pl.mg.rac.user.application.service.event.*;
import pl.mg.rac.user.domain.service.UserDomainService;
import pl.mg.rac.user.infrastructure.out.messaging.UserKafkaEventPublisher;
import pl.mg.rac.user.infrastructure.out.persistence.UserJpaRepository;
import pl.mg.rac.user.infrastructure.out.persistence.UserRepository;

import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final UserJpaRepository userJpaRepository;

    public InfrastructureConfig(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    //facade
    @Bean
    public UserFacade userFacade(EventApplicationService eventApplicationService) {
        return new UserFacade(userApplicationService(), userApplicationService(), userApplicationService(),
                userApplicationService(), userApplicationService(), userApplicationService(), userApplicationService(), eventApplicationService);
    }

    //application services
    @Bean
    UserApplicationService userApplicationService() {
        return new UserApplicationService(userDomainService(), userEventPublisher(), userDatabase());
    }

    @Bean
    @Lazy
    EventApplicationService eventApplicationService(Map<String, EventAdapter<RacEvent<?>>> eventAdapters) {
        return new EventApplicationService(eventAdapters);
    }

    //domain service
    @Bean
    UserDomainService userDomainService() {
        return new UserDomainService();
    }

    //outgoing port adapters
    @Bean
    UserDatabase userDatabase() {
        return new UserRepository(userJpaRepository);
    }

    @Bean
    public UserEventPublisher userEventPublisher() {
        return new UserKafkaEventPublisher();
    }

    //event adapters
    @Bean
    public EventAdapter<RacEvent<?>> rentRequestUserAdapter() {
        return new RentRequestUserEventAdapter(userDatabase(), userEventPublisher());
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentRequestFailedCarAdapter() {
        return new RentRequestFailedCarAdapter(userDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnRequestFailedCarAdapter() {
        return new ReturnRequestFailedCarAdapter(userDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnRequestUserAdapter() {
        return new ReturnRequestUserAdapter(userDatabase(), userEventPublisher());
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentSuccessAdapter() {
        return new RentSuccessEventAdapter(userDatabase());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnSuccessAdapter() {
        return new ReturnSuccessEventAdapter(userDatabase());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        KafkaTemplate<String, Object> stringStringKafkaTemplate = new KafkaTemplate<>(producerFactory);
        stringStringKafkaTemplate.setObservationEnabled(true);
        return stringStringKafkaTemplate;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
