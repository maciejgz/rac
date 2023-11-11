package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.command.ReturnCarCommand;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.response.ReturnCarResponse;

public interface ReturnCar {
    ReturnCarResponse returnCar(ReturnCarCommand command) throws CarNotFoundException;

}
