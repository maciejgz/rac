package pl.mg.rac.location.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.location.application.facade.LocationFacade;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.application.port.out.LocationEventPublisher;
import pl.mg.rac.location.application.port.out.UserLocationDatabase;
import pl.mg.rac.location.application.service.LocationApplicationService;
import pl.mg.rac.location.application.service.event.EventAdapter;
import pl.mg.rac.location.domain.service.LocationDomainService;
import pl.mg.rac.location.infrastructure.out.messaging.LocationKafkaEventPublisher;
import pl.mg.rac.location.infrastructure.out.persistence.CarLocationJpaRepository;
import pl.mg.rac.location.infrastructure.out.persistence.CarLocationRepository;
import pl.mg.rac.location.infrastructure.out.persistence.UserLocationJpaRepository;
import pl.mg.rac.location.infrastructure.out.persistence.UserLocationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final CarLocationJpaRepository carLocationJpaRepository;
    private final UserLocationJpaRepository userLocationJpaRepository;
    private final Map<String, EventAdapter<RacEvent<?>>> eventAdapters;

    public InfrastructureConfig(CarLocationJpaRepository carLocationJpaRepository, List<EventAdapter<RacEvent<?>>> eventAdapterList, UserLocationJpaRepository userLocationJpaRepository) {
        this.carLocationJpaRepository = carLocationJpaRepository;
        this.userLocationJpaRepository = userLocationJpaRepository;
        this.eventAdapters = new HashMap<>();
        for (EventAdapter<RacEvent<?>> eventAdapter : eventAdapterList) {
            this.eventAdapters.put(eventAdapter.getEventType(), eventAdapter);
        }
    }

    //facade
    @Bean
    public LocationFacade rentFacade() {
        return new LocationFacade(locationApplicationService(), locationApplicationService());
    }

    //application services
    @Bean
    public LocationApplicationService locationApplicationService() {
        return new LocationApplicationService(carLocationDatabase(), userLocationDatabase(), locationEventPublisher());
    }

    //domain service
    public LocationDomainService locationDomainService() {
        return new LocationDomainService();
    }

    //outgoing port adapters
    @Bean
    public CarLocationDatabase carLocationDatabase() {
        return new CarLocationRepository(carLocationJpaRepository);
    }

    @Bean
    public UserLocationDatabase userLocationDatabase() {
        return new UserLocationRepository(userLocationJpaRepository);
    }

    @Bean
    public LocationEventPublisher locationEventPublisher() {
        return new LocationKafkaEventPublisher();
    }

}
