package pl.mg.rac.user.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.user.application.service.event.EventAdapter;
import pl.mg.rac.user.application.service.event.RentRequestUserEventAdapter;

@Configuration
public class EventAdaptersConfig {

    @Bean
    @Qualifier("RAC_RENT_REQUEST_USER")
    public EventAdapter<RacEvent<?>> rentRequestUserAdapter() {
        return new RentRequestUserEventAdapter();
    }

}
