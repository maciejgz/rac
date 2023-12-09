package pl.mg.rac.rent.infrastructure.in.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.commons.api.dto.ApiError;
import pl.mg.rac.rent.application.dto.command.RequestRentCommand;
import pl.mg.rac.rent.application.dto.exception.CarAlreadyHasActiveRentException;
import pl.mg.rac.rent.application.dto.exception.UserAlreadyHasActiveRentException;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;
import pl.mg.rac.rent.application.facade.RentFacade;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/rent")
@Slf4j
public class RentCommandController {

    private final RentFacade rentFacade;

    public RentCommandController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @PostMapping(value = "")
    public ResponseEntity<RequestRentResponse> rentRequest(@RequestBody RequestRentCommand command) throws URISyntaxException, UserAlreadyHasActiveRentException, CarAlreadyHasActiveRentException {
        log.debug("rentRequest() called with: command = [" + command + "]");
        RequestRentResponse rent = rentFacade.requestRent(command);
        return ResponseEntity.created(new URI("/rent/" + rent.rentId())).body(rent);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleUserRentException(UserAlreadyHasActiveRentException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleCarRentException(CarAlreadyHasActiveRentException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

}
