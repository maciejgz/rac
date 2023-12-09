package pl.mg.rac.rent.application.service.monitor;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.domain.model.Rent;

import java.time.Instant;
import java.util.List;

@Slf4j
public class ReturnRequestMonitor {

    private final RentDatabase rentDatabase;

    public ReturnRequestMonitor(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    public void checkReturnRequests() {
        List<Rent> openRentRequests = rentDatabase.findOpenReturnRequests();
        log.debug("Checking {} return requests", openRentRequests.size());
        for (Rent rentRequest : openRentRequests) {
            if (isReturnTimeout(rentRequest)) {
                handleTimeout(rentRequest);
            }
        }
    }

    private void handleTimeout(Rent returnRequest) {
        returnRequest.handleReturnTimeout();
        rentDatabase.save(returnRequest);
        //TODO notify user about timeout over websocket
    }

    private boolean isReturnTimeout(Rent rentRequest) {
        return rentRequest.getReturnRequestTimestamp()
                .isBefore(Instant.now().minusSeconds(60));
    }
}
