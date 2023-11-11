package pl.mg.rac.car.infrastructure.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.car.application.dto.command.AddCarCommand;
import pl.mg.rac.car.application.dto.command.DeleteCarCommand;
import pl.mg.rac.car.application.dto.exception.CarAlreadyExistsException;
import pl.mg.rac.car.application.dto.exception.CarAlreadyNotExistException;
import pl.mg.rac.car.application.dto.response.AddCarResponse;
import pl.mg.rac.car.application.dto.response.DeleteCarResponse;
import pl.mg.rac.car.application.facade.CarFacade;

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
        log.debug("Blacklist user {}", command);
        AddCarResponse addCarResponse = carFacade.addCar(command);
        return ResponseEntity.created(new URI("/car/" + addCarResponse.vin())).body(addCarResponse);
    }

    @DeleteMapping(value = "/{vin}")
    public ResponseEntity<Void> deleteUser(@PathVariable String vin) throws CarAlreadyNotExistException {
        DeleteCarResponse response = carFacade.deleteCar(new DeleteCarCommand(vin));
        log.debug("deleteUser() deleted: vin = [" + response.vin() + "]");
        return ResponseEntity.noContent().build();
    }

    //TODO implement missing command endpoints

    //TODO implement exception handlers
}
