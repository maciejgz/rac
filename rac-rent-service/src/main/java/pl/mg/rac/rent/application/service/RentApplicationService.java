package pl.mg.rac.rent.application.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rent.RentRequestUserEvent;
import pl.mg.rac.commons.event.rent.payload.RentRequestUserPayload;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;
import pl.mg.rac.rent.application.port.in.RequestRent;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.domain.model.Rent;

@Slf4j
public class RentApplicationService implements RequestRent {

    private final RentDatabase rentDatabase;
    private final RentEventPublisher rentEventPublisher;

    public RentApplicationService(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher) {
        this.rentDatabase = rentDatabase;
        this.rentEventPublisher = rentEventPublisher;
    }

    @Override
    public RequestRentResponse requestRent(String username, String vin, Location location) {
        Rent rent = Rent.requestRent(username, vin, location);
        Rent savedRentRequest = rentDatabase.save(rent);
        RentRequestUserEvent rentRequestUserEvent = new RentRequestUserEvent(savedRentRequest.getRentId(),
                new RentRequestUserPayload(savedRentRequest.getRentId(),
                        savedRentRequest.getUsername(),
                        savedRentRequest.getVin()));
        rentEventPublisher.publishRentEvent(rentRequestUserEvent);
        //TODO handle exceptions
        return new RequestRentResponse(savedRentRequest.getRentId(),
                savedRentRequest.getUsername(),
                savedRentRequest.getVin(),
                savedRentRequest.getStatus());
    }
}
