package pl.mg.rac.location.application.port.in;

import pl.mg.rac.location.application.dto.exception.LocationUpdateException;

public interface UpdateCarLocation {

    public void updateCarLocation(String carId, String locationId) throws LocationUpdateException;

}
