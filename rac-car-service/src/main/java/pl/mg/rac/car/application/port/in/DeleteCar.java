package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.command.DeleteCarCommand;
import pl.mg.rac.car.application.dto.exception.CarAlreadyNotExistException;
import pl.mg.rac.car.application.dto.exception.CarRentedException;
import pl.mg.rac.car.application.dto.response.DeleteCarResponse;

public interface DeleteCar {

    DeleteCarResponse deleteCar(DeleteCarCommand command) throws CarAlreadyNotExistException, CarRentedException;

}
