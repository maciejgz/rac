package pl.mg.rac.rent.infrastructure.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.commons.api.dto.ApiError;
import pl.mg.rac.rent.application.dto.exception.RentNotFoundException;
import pl.mg.rac.rent.application.dto.query.GetRentByIdQuery;
import pl.mg.rac.rent.application.dto.response.RentResponse;
import pl.mg.rac.rent.application.facade.RentFacade;

@RestController
@RequestMapping("/rent")
@Slf4j
public class RentQueryController {

    private final RentFacade rentFacade;

    public RentQueryController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @GetMapping(value = "/{rentId}")
    public ResponseEntity<RentResponse> getRent(@PathVariable String rentId) throws RentNotFoundException {
        log.debug("getRent() called with: rentId = [" + rentId + "]");
        RentResponse rent = rentFacade.getRent(new GetRentByIdQuery(rentId));
        return ResponseEntity.ok(rent);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleDeletionException(RentNotFoundException ignoredE) {
        return ResponseEntity.notFound().build();
    }
}
