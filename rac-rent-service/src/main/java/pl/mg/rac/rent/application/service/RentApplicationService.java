package pl.mg.rac.rent.application.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;

@Slf4j
public class RentApplicationService {

    private final RentDatabase rentDatabase;
    private final RentEventPublisher rentEventPublisher;

    public RentApplicationService(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher) {
        this.rentDatabase = rentDatabase;
        this.rentEventPublisher = rentEventPublisher;
    }
}
