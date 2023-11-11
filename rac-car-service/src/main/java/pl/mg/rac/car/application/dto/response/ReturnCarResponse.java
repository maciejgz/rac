package pl.mg.rac.car.application.dto.response;

import pl.mg.rac.commons.value.Location;

public record ReturnCarResponse(String vin, double distanceTraveled, String rentalId, Location location,
                                boolean success) {
}
