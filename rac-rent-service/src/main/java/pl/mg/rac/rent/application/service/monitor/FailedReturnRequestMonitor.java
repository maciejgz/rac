package pl.mg.rac.rent.application.service.monitor;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rentreturn.ReturnSuccessEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnSuccessPayload;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.domain.model.Rent;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class simulates monitoring of failed return requests. Administrator is responsible for accepting or
 * declining return requests blocked by some reason.
 */
@Slf4j
public class FailedReturnRequestMonitor {

    private final RentDatabase rentDatabase;
    private final RentEventPublisher eventPublisher;

    public FailedReturnRequestMonitor(RentDatabase rentDatabase, RentEventPublisher eventPublisher) {
        this.rentDatabase = rentDatabase;
        this.eventPublisher = eventPublisher;
    }

    public void unlockFailedReturnRequests() {
        List<Rent> openRentRequests = rentDatabase.findDeclinedReturnRequests();
        log.debug("Checking declined {} return requests", openRentRequests.size());
        for (Rent rentRequest : openRentRequests) {
            try {
                rentRequest.moveToReturnAcceptedByAdminStatus();
                rentDatabase.save(rentRequest);
                eventPublisher.publishRentEvent(
                        new ReturnSuccessEvent(
                                rentRequest.getId(),
                                new ReturnSuccessPayload(
                                        rentRequest.getRentId(),
                                        rentRequest.getUsername(),
                                        rentRequest.getVin(),
                                        rentRequest.getEndLocation(),
                                        rentRequest.getDistanceTraveled(),
                                        BigDecimal.ZERO)
                        )
                );
            } catch (IllegalStateException e) {
                log.error("Failed to unlock return request {}", rentRequest.getId(), e);
            }
        }
    }

}
