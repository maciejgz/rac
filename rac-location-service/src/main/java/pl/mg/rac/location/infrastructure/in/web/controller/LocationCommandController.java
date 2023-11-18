package pl.mg.rac.location.infrastructure.in.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.commons.api.dto.ApiError;
import pl.mg.rac.location.application.dto.command.UpdateCarLocationCommand;
import pl.mg.rac.location.application.dto.command.UpdateUserLocationCommand;
import pl.mg.rac.location.application.dto.exception.LocationUpdateException;
import pl.mg.rac.location.application.facade.LocationFacade;

@RestController
@RequestMapping("/location")
@Slf4j
public class LocationCommandController {

    private final LocationFacade locationFacade;

    public LocationCommandController(LocationFacade locationFacade) {
        this.locationFacade = locationFacade;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Void> updateUserLocation(@RequestBody UpdateUserLocationCommand command) throws LocationUpdateException {
        log.debug("updateUserLocation() called with: command = [" + command + "]");
        locationFacade.updateUserLocation(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/car")
    public ResponseEntity<Void> updateCarLocation(@RequestBody UpdateCarLocationCommand command) throws LocationUpdateException {
        log.debug("updateCarLocation() called with: command = [" + command + "]");
        locationFacade.updateCarLocation(command);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleDeletionException(LocationUpdateException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

}
