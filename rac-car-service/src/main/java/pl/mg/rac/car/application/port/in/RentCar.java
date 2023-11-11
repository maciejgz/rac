package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.command.RentCarCommand;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.response.RentCarResponse;

public interface RentCar {

    RentCarResponse rentCar(RentCarCommand command) throws CarNotFoundException;
}
