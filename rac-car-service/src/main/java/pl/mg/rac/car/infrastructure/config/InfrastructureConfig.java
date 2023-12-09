package pl.mg.rac.car.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.mg.rac.car.application.facade.CarFacade;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.application.service.CarApplicationService;
import pl.mg.rac.car.application.service.EventApplicationService;
import pl.mg.rac.car.application.service.event.EventAdapter;
import pl.mg.rac.car.application.service.event.RentRequestCarAdapter;
import pl.mg.rac.car.application.service.event.RentSuccessAdapter;
import pl.mg.rac.car.application.service.event.ReturnRequestCarAdapter;
import pl.mg.rac.car.domain.service.CarDomainService;
import pl.mg.rac.car.infrastructure.out.messaging.CarKafkaEventPublisher;
import pl.mg.rac.car.infrastructure.out.persistence.CarJpaRepository;
import pl.mg.rac.car.infrastructure.out.persistence.CarRepository;
import pl.mg.rac.commons.event.RacEvent;

import java.util.Map;

@Configuration
@Slf4j
public class InfrastructureConfig {

    private final CarJpaRepository carJpaRepository;

    public InfrastructureConfig(CarJpaRepository carJpaRepository) {
        this.carJpaRepository = carJpaRepository;
    }

    //facade
    @Bean
    public CarFacade carFacade(EventApplicationService eventApplicationService) {
        return new CarFacade(carApplicationService(), carApplicationService(),
                carApplicationService(), carApplicationService(),
                carApplicationService(), carApplicationService(),
                eventApplicationService,
                carApplicationService(),
                carApplicationService());
    }

    //application services
    @Bean
    public CarApplicationService carApplicationService() {
        return new CarApplicationService(carDatabase(), eventPublisher());
    }

    @Bean
    @Lazy
    EventApplicationService eventApplicationService(Map<String, EventAdapter<RacEvent<?>>> eventAdapters) {
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
    public CarEventPublisher eventPublisher() {
        return new CarKafkaEventPublisher();
    }


    //event adapters
    @Bean
    public EventAdapter<RacEvent<?>> rentRequestCarAdapter() {
        return new RentRequestCarAdapter(carDatabase(), eventPublisher());
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnRequestCarAdapter() {
        return new ReturnRequestCarAdapter(carDatabase(), eventPublisher());
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentSuccessAdapter() {
        return new RentSuccessAdapter(carDatabase());
    }

}
