package pl.mg.rac.location.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.location.application.facade.LocationFacade;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.application.port.out.LocationEventPublisher;
import pl.mg.rac.location.application.port.out.UserLocationDatabase;
import pl.mg.rac.location.application.service.EventApplicationService;
import pl.mg.rac.location.application.service.LocationApplicationService;
import pl.mg.rac.location.application.service.event.EventAdapter;
import pl.mg.rac.location.application.service.event.ReturnLocationEventAdapter;
import pl.mg.rac.location.domain.service.CarLocationDomainService;
import pl.mg.rac.location.infrastructure.out.messaging.LocationKafkaEventPublisher;
import pl.mg.rac.location.infrastructure.out.persistence.CarLocationJpaRepository;
import pl.mg.rac.location.infrastructure.out.persistence.CarLocationRepository;
import pl.mg.rac.location.infrastructure.out.persistence.UserLocationJpaRepository;
import pl.mg.rac.location.infrastructure.out.persistence.UserLocationRepository;

import java.util.Map;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final CarLocationJpaRepository carLocationJpaRepository;
    private final UserLocationJpaRepository userLocationJpaRepository;

    public InfrastructureConfig(CarLocationJpaRepository carLocationJpaRepository, UserLocationJpaRepository userLocationJpaRepository) {
        this.carLocationJpaRepository = carLocationJpaRepository;
        this.userLocationJpaRepository = userLocationJpaRepository;
    }

    //facade
    @Bean
    public LocationFacade Facade(EventApplicationService eventApplicationService) {
        return new LocationFacade(locationApplicationService(), locationApplicationService(),
                locationApplicationService(), locationApplicationService(), eventApplicationService);
    }

    //application services
    @Bean
    public LocationApplicationService locationApplicationService() {
        return new LocationApplicationService(carLocationDatabase(), userLocationDatabase(), locationEventPublisher());
    }

    @Lazy
    @Bean
    public EventApplicationService eventApplicationService(Map<String, EventAdapter<RacEvent<?>>> eventAdapters) {
        return new EventApplicationService(eventAdapters);
    }

    //domain service
    public CarLocationDomainService locationDomainService() {
        return new CarLocationDomainService();
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

    // adapters
    @Bean
    public EventAdapter<RacEvent<?>> rentRequestLocationAdapter() {
        return new ReturnLocationEventAdapter(carLocationDatabase(), locationEventPublisher(), locationDomainService());
    }

}
