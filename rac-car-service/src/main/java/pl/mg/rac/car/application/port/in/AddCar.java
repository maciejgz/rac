package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.command.AddCarCommand;
import pl.mg.rac.car.application.dto.exception.CarAlreadyExistsException;
import pl.mg.rac.car.application.dto.response.AddCarResponse;

public interface AddCar {

    AddCarResponse addCar(AddCarCommand command) throws CarAlreadyExistsException;

}
