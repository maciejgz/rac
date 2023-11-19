package pl.mg.rac.location.application.facade;

import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.dto.command.UpdateCarLocationCommand;
import pl.mg.rac.location.application.dto.command.UpdateUserLocationCommand;
import pl.mg.rac.location.application.dto.exception.LocationNotFoundException;
import pl.mg.rac.location.application.dto.exception.LocationUpdateException;
import pl.mg.rac.location.application.port.in.GetCarLocation;
import pl.mg.rac.location.application.port.in.GetUserLocation;
import pl.mg.rac.location.application.port.in.UpdateCarLocation;
import pl.mg.rac.location.application.port.in.UpdateUserLocation;
import pl.mg.rac.location.application.service.EventApplicationService;

public class LocationFacade {

    private final UpdateCarLocation updateCarLocationAdapter;
    private final UpdateUserLocation updateUserLocationAdapter;
    private final GetCarLocation getCarLocationAdapter;
    private final GetUserLocation getUserLocationAdapter;
    private final EventApplicationService eventApplicationService;

    public LocationFacade(UpdateCarLocation updateCarLocationAdapter, UpdateUserLocation updateUserLocationAdapter,
                          GetCarLocation getCarLocationAdapter, GetUserLocation getUserLocationAdapter, EventApplicationService eventApplicationService) {
        this.updateCarLocationAdapter = updateCarLocationAdapter;
        this.updateUserLocationAdapter = updateUserLocationAdapter;
        this.getCarLocationAdapter = getCarLocationAdapter;
        this.getUserLocationAdapter = getUserLocationAdapter;
        this.eventApplicationService = eventApplicationService;
    }

    public void updateUserLocation(UpdateUserLocationCommand command) throws LocationUpdateException {
        updateUserLocationAdapter.updateUserLocation(command);
    }

    public void updateCarLocation(UpdateCarLocationCommand command) throws LocationUpdateException {
        updateCarLocationAdapter.updateCarLocation(command);
    }

    public Location getCarLocation(String vin) throws LocationNotFoundException {
        return getCarLocationAdapter.getCarLocation(vin);
    }

    public Location getUserLocation(String username) throws LocationNotFoundException {
        return getUserLocationAdapter.getUserLocation(username);
    }

    public void handleIncomingEvent(RacEvent<?> event) {
        eventApplicationService.handleIncomingEvent(event);
    }
}
