package pl.mg.rac.rent.application.service.monitor;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.domain.model.Rent;

import java.time.Instant;
import java.util.List;

@Slf4j
public class RentRequestMonitor {

    private final RentDatabase rentDatabase;

    public RentRequestMonitor(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    public void checkRentRequests() {
        List<Rent> openRentRequests = rentDatabase.findOpenRentRequests();
        log.debug("Checking {} rent requests", openRentRequests.size());
        for (Rent rentRequest : openRentRequests) {
            if (isRentTimeout(rentRequest)) {
                handleTimeout(rentRequest);
            }
        }
    }

    private void handleTimeout(Rent rentRequest) {
        rentRequest.handleRentTimeout();
        rentDatabase.save(rentRequest);
        //TODO notify user about timeout over websocket
        //TODO all services should receive compensation event - car and user to be added
    }

    private boolean isRentTimeout(Rent rentRequest) {
        return rentRequest.getRentRequestTimestamp()
                .isBefore(Instant.now().minusSeconds(60));
    }
}
