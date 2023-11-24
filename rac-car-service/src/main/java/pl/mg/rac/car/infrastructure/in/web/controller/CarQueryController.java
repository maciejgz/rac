package pl.mg.rac.car.infrastructure.in.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.query.GetCarQuery;
import pl.mg.rac.car.application.dto.response.GetCarResponse;
import pl.mg.rac.car.application.facade.CarFacade;

@RestController
@RequestMapping(value = "/car")
@Slf4j
public class CarQueryController {

    private final CarFacade carFacade;

    public CarQueryController(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    @GetMapping(value = "/random")
    public ResponseEntity<GetCarResponse> getRandomUser() throws CarNotFoundException {
        GetCarResponse car = carFacade.getRandomCar();
        return ResponseEntity.ok(car);
    }

    @GetMapping(value = "/{vin}")
    public ResponseEntity<GetCarResponse> getUser(@PathVariable String vin) throws CarNotFoundException {
        GetCarResponse car = carFacade.getCar(new GetCarQuery(vin));
        return ResponseEntity.ok(car);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleException(CarNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
