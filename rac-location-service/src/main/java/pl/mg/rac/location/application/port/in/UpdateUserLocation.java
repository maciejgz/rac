package pl.mg.rac.location.application.port.in;

import pl.mg.rac.location.application.dto.exception.LocationUpdateException;

public interface UpdateUserLocation {

    public void updateUserLocation(String username, String locationId) throws LocationUpdateException;
}
