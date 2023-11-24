package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.response.GetCarResponse;

public interface GetRandomCar {

    GetCarResponse getRandomCar() throws CarNotFoundException;
}
