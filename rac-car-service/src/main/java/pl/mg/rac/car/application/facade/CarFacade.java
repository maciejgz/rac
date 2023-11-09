package pl.mg.rac.car.application.facade;

import pl.mg.rac.car.application.dto.command.AddCarCommand;
import pl.mg.rac.car.application.dto.response.AddCarResponse;
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

    public AddCarResponse addCar(AddCarCommand command) {
        return null;
    }


}
