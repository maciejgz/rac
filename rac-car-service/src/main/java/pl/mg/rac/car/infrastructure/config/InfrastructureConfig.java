package pl.mg.rac.car.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.car.application.facade.CarFacade;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.application.service.CarApplicationService;
import pl.mg.rac.car.domain.service.CarDomainService;
import pl.mg.rac.car.infrastructure.out.messaging.CarKafkaEventPublisher;
import pl.mg.rac.car.infrastructure.out.persistence.CarJpaRepository;
import pl.mg.rac.car.infrastructure.out.persistence.CarRepository;

@Configuration
@Slf4j
public class InfrastructureConfig {

    private final CarJpaRepository carJpaRepository;

    public InfrastructureConfig(CarJpaRepository carJpaRepository) {
        this.carJpaRepository = carJpaRepository;
    }

    //facade
    @Bean
    public CarFacade carFacade() {
        return new CarFacade(carApplicationService(), carApplicationService(), carApplicationService(), carApplicationService(), carApplicationService());
    }

    //application services
    @Bean
    public CarApplicationService carApplicationService() {
        return new CarApplicationService(carDatabase(), carMessageBroker());
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
