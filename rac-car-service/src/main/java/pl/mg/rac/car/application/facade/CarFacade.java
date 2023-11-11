package pl.mg.rac.car.application.facade;

import pl.mg.rac.car.application.dto.command.AddCarCommand;
import pl.mg.rac.car.application.dto.command.DeleteCarCommand;
import pl.mg.rac.car.application.dto.command.RentCarCommand;
import pl.mg.rac.car.application.dto.command.ReturnCarCommand;
import pl.mg.rac.car.application.dto.exception.CarAlreadyExistsException;
import pl.mg.rac.car.application.dto.exception.CarAlreadyNotExistException;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.query.GetCarQuery;
import pl.mg.rac.car.application.dto.response.*;
import pl.mg.rac.car.application.port.in.*;

public class CarFacade {

    private final AddCar addCarAdapter;
    private final DeleteCar deleteCarAdapter;
    private final RentCar rentCarAdapter;
    private final ReturnCar returnCarAdapter;
    private final GetCar getCarAdapter;

    public CarFacade(AddCar addCarAdapter, DeleteCar deleteCarAdapter, RentCar rentCarAdapter, ReturnCar returnCarAdapter, GetCar getCarAdapter) {
        this.addCarAdapter = addCarAdapter;
        this.deleteCarAdapter = deleteCarAdapter;
        this.rentCarAdapter = rentCarAdapter;
        this.returnCarAdapter = returnCarAdapter;
        this.getCarAdapter = getCarAdapter;
    }

    public AddCarResponse addCar(AddCarCommand command) throws CarAlreadyExistsException {
        return addCarAdapter.addCar(command);
    }

    public DeleteCarResponse deleteCar(DeleteCarCommand command) throws CarAlreadyNotExistException {
        return deleteCarAdapter.deleteCar(command);
    }


    public RentCarResponse rentCar(RentCarCommand command) throws CarNotFoundException {
        return rentCarAdapter.rentCar(command);
    }

    public ReturnCarResponse returnCar(ReturnCarCommand command) {
        return returnCarAdapter.returnCar(command);
    }

    public GetCarResponse getCar(GetCarQuery query) throws CarNotFoundException {
        return getCarAdapter.getCar(query);
    }

}
