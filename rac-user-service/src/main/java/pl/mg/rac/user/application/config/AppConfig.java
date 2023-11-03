package pl.mg.rac.user.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.user.application.service.UserApplicationService;

/**
 * TODO put app configuration here - adapter beans, etc.
 */
@Configuration
public class AppConfig {

    @Bean
    public UserApplicationService userApplicationService() {
        return new UserApplicationService();
    }
}
