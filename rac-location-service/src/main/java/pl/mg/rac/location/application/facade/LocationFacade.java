package pl.mg.rac.location.application.facade;

import pl.mg.rac.location.application.dto.command.UpdateCarLocationCommand;
import pl.mg.rac.location.application.dto.command.UpdateUserLocationCommand;
import pl.mg.rac.location.application.port.in.UpdateCarLocation;
import pl.mg.rac.location.application.port.in.UpdateUserLocation;

public class LocationFacade {

    private final UpdateCarLocation updateCarLocationAdapter;
    private final UpdateUserLocation updateUserLocationAdapter;

    public LocationFacade(UpdateCarLocation updateCarLocationAdapter, UpdateUserLocation updateUserLocationAdapter) {
        this.updateCarLocationAdapter = updateCarLocationAdapter;
        this.updateUserLocationAdapter = updateUserLocationAdapter;
    }

    public void updateUserLocation(UpdateUserLocationCommand command) {
        //TODO implement
    }

    public void updateCarLocation(UpdateCarLocationCommand command) {
        //TODO implement
    }
}
