package pl.mg.rac.rent.application.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rent.RentRequestUserEvent;
import pl.mg.rac.commons.event.rent.payload.RentRequestUserPayload;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestLocationEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestLocationPayload;
import pl.mg.rac.rent.application.dto.command.RequestRentCommand;
import pl.mg.rac.rent.application.dto.command.RequestReturnCommand;
import pl.mg.rac.rent.application.dto.exception.InvalidRentStateException;
import pl.mg.rac.rent.application.dto.exception.RentNotFoundException;
import pl.mg.rac.rent.application.dto.query.GetRentByIdQuery;
import pl.mg.rac.rent.application.dto.response.RentResponse;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;
import pl.mg.rac.rent.application.dto.response.RequestReturnResponse;
import pl.mg.rac.rent.application.port.in.GetRent;
import pl.mg.rac.rent.application.port.in.RequestRent;
import pl.mg.rac.rent.application.port.in.RequestReturn;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.domain.model.Rent;

import java.util.Optional;

@Slf4j
public class RentApplicationService implements RequestRent, GetRent, RequestReturn {

    private final RentDatabase rentDatabase;
    private final RentEventPublisher rentEventPublisher;

    public RentApplicationService(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher) {
        this.rentDatabase = rentDatabase;
        this.rentEventPublisher = rentEventPublisher;
    }

    @Override
    public RequestRentResponse requestRent(RequestRentCommand command) {
        Rent rent = Rent.requestRent(command.username(), command.vin(), command.location());
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

    @Override
    public RequestReturnResponse requestReturn(RequestReturnCommand command) throws RentNotFoundException, InvalidRentStateException {
        Optional<Rent> rent = rentDatabase.findById(command.rentId());
        if (rent.isPresent()) {
            try {
                rent.get().requestReturn();
                rentDatabase.save(rent.get());
                rentEventPublisher.publishRentEvent(
                        new ReturnRequestLocationEvent(
                                rent.get().getId(),
                                new ReturnRequestLocationPayload(rent.get().getId(), rent.get().getUsername(), rent.get().getVin())
                        )
                );
                return new RequestReturnResponse(rent.get().getRentId(),
                        rent.get().getUsername(),
                        rent.get().getVin(),
                        rent.get().getStatus(),
                        rent.get().getStatusReason());
            } catch (Exception e) {
                log.error("Error while requesting return", e);
                throw new InvalidRentStateException("Error while requesting return", e);
            }
        } else {
            throw new RentNotFoundException("Rent with id: " + command.rentId() + " not found");
        }
    }

    @Override
    public RentResponse getRent(GetRentByIdQuery query) throws RentNotFoundException {
        Rent rent = rentDatabase.findById(query.rentId()).orElseThrow(() -> new RentNotFoundException("Rent with id: " + query.rentId() + " not found"));
        return new RentResponse(
                rent.getRentId(),
                rent.getUsername(),
                rent.getVin(),
                rent.getStartLocation(),
                rent.getDistanceTraveled(),
                rent.getEndLocation(),
                rent.getRentRequestTimestamp(),
                rent.getRentStartTimestamp(),
                rent.getReturnRequestTimestamp(),
                rent.getRentEndTimestamp(),
                rent.getStatus(),
                rent.getStatusReason()
        );
    }
}
