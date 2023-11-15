package pl.mg.rac.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.user.application.facade.UserFacade;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.application.service.EventApplicationService;
import pl.mg.rac.user.application.service.UserApplicationService;
import pl.mg.rac.user.application.service.event.EventAdapter;
import pl.mg.rac.user.domain.service.UserDomainService;
import pl.mg.rac.user.infrastructure.out.messaging.UserKafkaEventPublisher;
import pl.mg.rac.user.infrastructure.out.persistence.UserJpaRepository;
import pl.mg.rac.user.infrastructure.out.persistence.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final UserJpaRepository userJpaRepository;
    private final Map<String, EventAdapter<RacEvent<?>>> eventAdapters;

    public InfrastructureConfig(UserJpaRepository userJpaRepository, List<EventAdapter<RacEvent<?>>> eventAdapterList) {
        this.userJpaRepository = userJpaRepository;
        this.eventAdapters = new HashMap<>();
        for (EventAdapter<RacEvent<?>> eventAdapter : eventAdapterList) {
            this.eventAdapters.put(eventAdapter.getEventType(), eventAdapter);
        }
    }

    //facade
    @Bean
    public UserFacade userFacade() {
        return new UserFacade(userApplicationService(), userApplicationService(), userApplicationService(), userApplicationService(), eventApplicationService());
    }

    //application services
    @Bean
    UserApplicationService userApplicationService() {
        return new UserApplicationService(userDomainService(), userEventPublisher(), userDatabase());
    }

    @Bean
    EventApplicationService eventApplicationService() {
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


}
