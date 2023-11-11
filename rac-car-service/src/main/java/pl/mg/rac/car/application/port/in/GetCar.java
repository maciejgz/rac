package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.query.GetCarQuery;
import pl.mg.rac.car.application.dto.response.GetCarResponse;

public interface GetCar {

    GetCarResponse getCar(GetCarQuery command) throws CarNotFoundException;
}
