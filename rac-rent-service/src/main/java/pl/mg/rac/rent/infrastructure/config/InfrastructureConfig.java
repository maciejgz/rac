package pl.mg.rac.rent.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.rent.application.facade.RentFacade;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.application.service.RentApplicationService;
import pl.mg.rac.rent.domain.service.RentDomainService;
import pl.mg.rac.rent.infrastructure.out.messaging.RentKafkaEventPublisher;
import pl.mg.rac.rent.infrastructure.out.persistence.RentJpaRepository;
import pl.mg.rac.rent.infrastructure.out.persistence.RentRepository;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class InfrastructureConfig {

    private final RentJpaRepository rentJpaRepository;

    public InfrastructureConfig(RentJpaRepository rentJpaRepository) {
        this.rentJpaRepository = rentJpaRepository;
    }

    //facade
    @Bean
    public RentFacade rentFacade() {
        return new RentFacade(rentApplicationService(), rentApplicationService(), rentApplicationService());
    }

    //application services
    @Bean
    public RentApplicationService rentApplicationService() {
        return new RentApplicationService(rentDatabase(), rentEventPublisher());
    }

    //domain service
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

}
