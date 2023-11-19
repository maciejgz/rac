package pl.mg.rac.rent.application.dto.response;

import pl.mg.rac.commons.value.Location;
import pl.mg.rac.rent.domain.model.RentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record RentResponse(
        String rentId,
        String username,
        String vin,
        Location startLocation,
        BigDecimal distanceTraveled,
        Location endLocation,
        Instant rentRequestTimestamp,
        Instant rentStartTimestamp,
        Instant returnRequestTimestamp,
        Instant rentEndTimestamp,
        RentStatus status,
        String statusReason
) {
}
