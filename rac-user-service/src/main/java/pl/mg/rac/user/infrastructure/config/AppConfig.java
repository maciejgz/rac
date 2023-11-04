package pl.mg.rac.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.user.application.facade.UserFacade;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.service.UserApplicationService;
import pl.mg.rac.user.domain.service.UserDomainService;
import pl.mg.rac.user.infrastructure.out.persistance.UserRepository;

/**
 * Put all the framework specific configuration here - adapter beans, etc.
 */
@Configuration
public class AppConfig {

    //facade
    @Bean
    public UserFacade userFacade() {
        return new UserFacade(userApplicationService(), userApplicationService(), userApplicationService(), userApplicationService());
    }

    //application services
    @Bean
    UserApplicationService userApplicationService() {
        return new UserApplicationService(userDomainService());
    }

    //adapter beans
    @Bean
    UserDomainService userDomainService() {
        return new UserDomainService(userDatabase());
    }

    @Bean
    UserDatabase userDatabase() {
        return new UserRepository();
    }


}
