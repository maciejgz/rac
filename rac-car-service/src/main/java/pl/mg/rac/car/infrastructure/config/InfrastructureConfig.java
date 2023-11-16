package pl.mg.rac.car.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.car.application.facade.CarFacade;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.application.service.CarApplicationService;
import pl.mg.rac.car.application.service.EventApplicationService;
import pl.mg.rac.car.application.service.event.EventAdapter;
import pl.mg.rac.car.domain.service.CarDomainService;
import pl.mg.rac.car.infrastructure.out.messaging.CarKafkaEventPublisher;
import pl.mg.rac.car.infrastructure.out.persistence.CarJpaRepository;
import pl.mg.rac.car.infrastructure.out.persistence.CarRepository;
import pl.mg.rac.commons.event.RacEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class InfrastructureConfig {

    private final CarJpaRepository carJpaRepository;
    private final Map<String, EventAdapter<RacEvent<?>>> eventAdapters;

    public InfrastructureConfig(CarJpaRepository carJpaRepository, List<EventAdapter<RacEvent<?>>> eventAdapterList) {
        this.carJpaRepository = carJpaRepository;
        this.eventAdapters = new HashMap<>();
        for (EventAdapter<RacEvent<?>> eventAdapter : eventAdapterList) {
            this.eventAdapters.put(eventAdapter.getEventType(), eventAdapter);
        }
    }

    //facade
    @Bean
    public CarFacade carFacade() {
        return new CarFacade(carApplicationService(), carApplicationService(),
                carApplicationService(), carApplicationService(),
                carApplicationService(), eventApplicationService());
    }

    //application services
    @Bean
    public CarApplicationService carApplicationService() {
        return new CarApplicationService(carDatabase(), carMessageBroker());
    }

    @Bean
    public EventApplicationService eventApplicationService() {
        return new EventApplicationService(eventAdapters);
    }

    //domain service
    @Bean
    public CarDomainService carDomainService() {
        return new CarDomainService();
    }

    // adapters
    @Bean
    public CarDatabase carDatabase() {
        return new CarRepository(carJpaRepository);
    }

    @Bean
    public CarEventPublisher carMessageBroker() {
        return new CarKafkaEventPublisher();
    }

}
