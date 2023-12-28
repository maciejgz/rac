package pl.mg.rac.car.infrastructure.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.car.application.dto.command.AddCarCommand;
import pl.mg.rac.car.application.dto.command.DeleteCarCommand;
import pl.mg.rac.car.application.dto.command.ReportCarFailureCommand;
import pl.mg.rac.car.application.dto.command.ReportCarFailureFixCommand;
import pl.mg.rac.car.application.dto.exception.CarAlreadyExistsException;
import pl.mg.rac.car.application.dto.exception.CarAlreadyNotExistException;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.exception.CarRentedException;
import pl.mg.rac.car.application.dto.response.AddCarResponse;
import pl.mg.rac.car.application.dto.response.DeleteCarResponse;
import pl.mg.rac.car.application.dto.response.ReportCarFailureFixResponse;
import pl.mg.rac.car.application.dto.response.ReportCarFailureResponse;
import pl.mg.rac.car.application.facade.CarFacade;
import pl.mg.rac.commons.api.dto.ApiError;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/car")
@Slf4j
public class CarCommandController {

    private final CarFacade carFacade;

    public CarCommandController(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    @PostMapping(value = "")
    public ResponseEntity<AddCarResponse> addCar(@RequestBody AddCarCommand command) throws URISyntaxException, CarAlreadyExistsException {
        log.debug("addCar() called with: command = [" + command + "]");
        AddCarResponse addCarResponse = carFacade.addCar(command);
        return ResponseEntity.created(new URI("/car/" + addCarResponse.vin())).body(addCarResponse);
    }

    @DeleteMapping(value = "/{vin}")
    public ResponseEntity<Void> deleteCar(@PathVariable String vin) throws CarAlreadyNotExistException, CarRentedException {
        log.debug("deleteUser() called with: vin = [" + vin + "]");
        DeleteCarResponse response = carFacade.deleteCar(new DeleteCarCommand(vin));
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{vin}/failure")
    public ResponseEntity<ReportCarFailureResponse> reportCarFailure(@RequestBody ReportCarFailureCommand command) throws CarNotFoundException {
        log.debug("reportCarFailure() called with: command = [" + command + "]");
        ReportCarFailureResponse reportCarFailureResponse = carFacade.reportCarFailure(command);
        return ResponseEntity.ok(reportCarFailureResponse);
    }

    @PutMapping(value = "/{vin}/fix")
    public ResponseEntity<ReportCarFailureFixResponse> reportCarFailureFix(@RequestBody ReportCarFailureFixCommand command) throws CarNotFoundException {
        log.debug("reportCarFailureFix() called with: command = [" + command + "]");
        ReportCarFailureFixResponse reportCarFailureFixResponse = carFacade.reportCarFailureFix(command);
        return ResponseEntity.ok(reportCarFailureFixResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleCarAlreadyNotExistException(CarAlreadyNotExistException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleCarAlreadyExistException(CarAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleCarRentedException(CarRentedException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleCarNotFoundException(CarNotFoundException ignoredE) {
        return ResponseEntity.notFound().build();
    }
}
