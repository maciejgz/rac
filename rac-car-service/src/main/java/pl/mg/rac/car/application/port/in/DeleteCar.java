package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.command.DeleteCarCommand;
import pl.mg.rac.car.application.dto.response.DeleteCarResponse;

public interface DeleteCar {

    DeleteCarResponse deleteCar(DeleteCarCommand command);

}
