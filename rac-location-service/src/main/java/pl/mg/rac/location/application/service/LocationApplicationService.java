package pl.mg.rac.location.application.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.dto.command.UpdateCarLocationCommand;
import pl.mg.rac.location.application.dto.command.UpdateUserLocationCommand;
import pl.mg.rac.location.application.dto.exception.LocationNotFoundException;
import pl.mg.rac.location.application.dto.exception.LocationUpdateException;
import pl.mg.rac.location.application.port.in.GetCarLocation;
import pl.mg.rac.location.application.port.in.GetUserLocation;
import pl.mg.rac.location.application.port.in.UpdateCarLocation;
import pl.mg.rac.location.application.port.in.UpdateUserLocation;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.application.port.out.LocationEventPublisher;
import pl.mg.rac.location.application.port.out.UserLocationDatabase;
import pl.mg.rac.location.domain.model.CarLocation;
import pl.mg.rac.location.domain.model.UserLocation;

import java.time.Instant;

@Slf4j
public class LocationApplicationService implements UpdateCarLocation, UpdateUserLocation, GetCarLocation, GetUserLocation {

    private final CarLocationDatabase carLocationDatabase;
    private final UserLocationDatabase userLocationDatabase;

    public LocationApplicationService(CarLocationDatabase carLocationDatabase, UserLocationDatabase userLocationDatabase, LocationEventPublisher eventPublisher) {
        this.carLocationDatabase = carLocationDatabase;
        this.userLocationDatabase = userLocationDatabase;
    }

    @Override
    public void updateCarLocation(UpdateCarLocationCommand command) throws LocationUpdateException {
        log.debug("updateCarLocation() called with: command = [" + command + "]");
        try {
            CarLocation location = new CarLocation(
                    command.vin(),
                    command.location(),
                    Instant.now());
            carLocationDatabase.saveCarLocation(location);
        } catch (Exception e) {
            throw new LocationUpdateException("Error while updating car location", e);
        }
    }

    @Override
    public void updateUserLocation(UpdateUserLocationCommand command) throws LocationUpdateException {
        log.debug("updateUserLocation() called with: command = [" + command + "]");
        try {
            UserLocation location = new UserLocation(
                    command.username(),
                    command.location(),
                    Instant.now());
            userLocationDatabase.saveUserLocation(location);
        } catch (Exception e) {
            throw new LocationUpdateException("Error while updating car location", e);
        }
    }

    @Override
    public Location getCarLocation(String vin) throws LocationNotFoundException {
        return carLocationDatabase.findLatestLocation(vin).getLocation();
    }

    @Override
    public Location getUserLocation(String username) throws LocationNotFoundException {
        return userLocationDatabase.findLatestLocation(username).getLocation();
    }
}
