package pl.mg.rac.rent.infrastructure.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.commons.api.dto.ApiError;
import pl.mg.rac.rent.application.dto.command.RequestReturnCommand;
import pl.mg.rac.rent.application.dto.exception.InvalidRentStateException;
import pl.mg.rac.rent.application.dto.exception.RentNotFoundException;
import pl.mg.rac.rent.application.dto.response.RequestReturnResponse;
import pl.mg.rac.rent.application.facade.RentFacade;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/return")
@Slf4j
public class ReturnCommandController {

    private final RentFacade rentFacade;

    public ReturnCommandController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @PutMapping(value = "/{rentId}")
    public ResponseEntity<RequestReturnResponse> returnRequest(@PathVariable String rentId) throws URISyntaxException, InvalidRentStateException, RentNotFoundException {
        log.debug("returnRequest() called with: rentId = [" + rentId + "]");
        RequestReturnResponse returnResponse = rentFacade.requestReturn(new RequestReturnCommand(rentId));
        return ResponseEntity.created(new URI("/rent/" + returnResponse.rentId())).body(returnResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleDeletionException(RentNotFoundException ignoredE) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleDeletionException(InvalidRentStateException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

}
