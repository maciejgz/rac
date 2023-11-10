package pl.mg.rac.car.infrastructure.in.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.rac.car.application.dto.query.GetCarQuery;
import pl.mg.rac.car.application.dto.response.GetCarResponse;
import pl.mg.rac.car.application.facade.CarFacade;
import pl.mg.rac.car.infrastructure.out.persistence.CarRepository;

@RestController
@RequestMapping(value = "/car")
@Slf4j
public class CarQueryController {

    private final CarFacade carFacade;

    public CarQueryController(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    @GetMapping(value = "/{vin}")
    public ResponseEntity<GetCarResponse> getUser(@PathVariable String vin) {
        GetCarResponse car = carFacade.getCar(new GetCarQuery(vin));
        return ResponseEntity.ok(car);
    }
}
