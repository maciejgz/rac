package pl.mg.rac.location.application.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.location.application.dto.exception.LocationUpdateException;
import pl.mg.rac.location.application.port.in.UpdateCarLocation;
import pl.mg.rac.location.application.port.in.UpdateUserLocation;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.application.port.out.LocationEventPublisher;
import pl.mg.rac.location.application.port.out.UserLocationDatabase;

@Slf4j
public class LocationApplicationService implements UpdateCarLocation, UpdateUserLocation {

    private final CarLocationDatabase carLocationDatabase;
    private final UserLocationDatabase userLocationDatabase;
    private final LocationEventPublisher eventPublisher;

    public LocationApplicationService(CarLocationDatabase carLocationDatabase, UserLocationDatabase userLocationDatabase, LocationEventPublisher eventPublisher) {
        this.carLocationDatabase = carLocationDatabase;
        this.userLocationDatabase = userLocationDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void updateCarLocation(String carId, String locationId) throws LocationUpdateException {
        log.debug("updateCarLocation() called with: carId = [" + carId + "], locationId = [" + locationId + "]");
        //TODO implement
    }

    @Override
    public void updateUserLocation(String username, String locationId) throws LocationUpdateException {
        log.debug("updateUserLocation() called with: username = [" + username + "], locationId = [" + locationId + "]");
        //TODO implement
    }
}
