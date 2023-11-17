package pl.mg.rac.rent.application.dto.response;

import pl.mg.rac.rent.domain.model.RentStatus;

public record RequestReturnResponse(String rentId, String username, String vin, RentStatus status, String statusMessage) {
}
