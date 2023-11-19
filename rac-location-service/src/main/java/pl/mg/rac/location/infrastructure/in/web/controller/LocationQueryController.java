package pl.mg.rac.location.infrastructure.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.dto.exception.LocationNotFoundException;
import pl.mg.rac.location.application.facade.LocationFacade;

@Slf4j
@RestController
@RequestMapping(value = "/location")
public class LocationQueryController {


    private final LocationFacade locationFacade;

    public LocationQueryController(LocationFacade locationFacade) {
        this.locationFacade = locationFacade;
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<Location> getUserLocation(@PathVariable String username) throws LocationNotFoundException {
        log.debug("getUserLocation() called with: username = [" + username + "]");
        Location userLocation = locationFacade.getUserLocation(username);
        return ResponseEntity.ok(userLocation);
    }

    @GetMapping(value = "/car/{vin}")
    public ResponseEntity<Location> getCarLocation(@PathVariable String vin) throws LocationNotFoundException {
        log.debug("getUserLocation() called with: vin = [" + vin + "]");
        Location carLocation = locationFacade.getCarLocation(vin);
        return ResponseEntity.ok(carLocation);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleDeletionException(LocationNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

}
