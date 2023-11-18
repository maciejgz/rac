package pl.mg.rac.car.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.application.service.event.EventAdapter;
import pl.mg.rac.car.application.service.event.RentRequestCarAdapter;
import pl.mg.rac.car.application.service.event.ReturnRequestCarAdapter;
import pl.mg.rac.commons.event.RacEvent;

@Configuration
public class EventAdaptersConfig {

    //FIXME get rid of circular dependency
    private final CarDatabase carDatabase;
    private final CarEventPublisher eventPublisher;

    public EventAdaptersConfig(CarDatabase carDatabase, CarEventPublisher eventPublisher) {
        this.carDatabase = carDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Bean
    public EventAdapter<RacEvent<?>> rentRequestCarAdapter() {
        return new RentRequestCarAdapter(carDatabase, eventPublisher);
    }

    @Bean
    public EventAdapter<RacEvent<?>> returnRequestCarAdapter() {
        return new ReturnRequestCarAdapter(carDatabase, eventPublisher);
    }

}
