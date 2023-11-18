package pl.mg.rac.location.application.port.in;

import pl.mg.rac.location.application.dto.command.UpdateUserLocationCommand;
import pl.mg.rac.location.application.dto.exception.LocationUpdateException;

public interface UpdateUserLocation {

    void updateUserLocation(UpdateUserLocationCommand command) throws LocationUpdateException;
}
