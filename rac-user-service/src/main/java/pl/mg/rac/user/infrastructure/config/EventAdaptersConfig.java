package pl.mg.rac.user.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.application.service.event.EventAdapter;
import pl.mg.rac.user.application.service.event.RentRequestFailedCarAdapter;
import pl.mg.rac.user.application.service.event.RentRequestUserEventAdapter;
import pl.mg.rac.user.application.service.event.ReturnRequestFailedCarAdapter;

@Configuration
public class EventAdaptersConfig {

    private final UserDatabase userDatabase;
    private final UserEventPublisher userEventPublisher;

    public EventAdaptersConfig(UserDatabase userDatabase, UserEventPublisher userEventPublisher) {
        this.userDatabase = userDatabase;
        this.userEventPublisher = userEventPublisher;
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentRequestUserAdapter() {
        return new RentRequestUserEventAdapter(userDatabase, userEventPublisher);
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentRequestFailedCarAdapter() {
        return new RentRequestFailedCarAdapter(userDatabase);
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnRequestFailedCarAdapter() {
        return new ReturnRequestFailedCarAdapter(userDatabase);
    }

}
