package pl.mg.rac.car.application.service;

import pl.mg.rac.car.application.dto.command.AddCarCommand;
import pl.mg.rac.car.application.dto.command.DeleteCarCommand;
import pl.mg.rac.car.application.dto.command.RentCarCommand;
import pl.mg.rac.car.application.dto.command.ReturnCarCommand;
import pl.mg.rac.car.application.dto.query.GetCarQuery;
import pl.mg.rac.car.application.dto.response.*;
import pl.mg.rac.car.application.port.in.*;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;

public class CarApplicationService implements AddCar, DeleteCar, RentCar, ReturnCar, GetCar {

    private final CarDatabase carDatabase;
    private final CarEventPublisher messageBroker;

    public CarApplicationService(CarDatabase carDatabase, CarEventPublisher messageBroker) {
        this.carDatabase = carDatabase;
        this.messageBroker = messageBroker;
    }

    @Override
    public AddCarResponse addCar(AddCarCommand command) {
        //TODO implement
        return null;
    }

    @Override
    public DeleteCarResponse deleteCar(DeleteCarCommand command) {
        //TODO implement
        return null;
    }

    @Override
    public RentCarResponse rentCar(RentCarCommand command) {
        //TODO implement
        return null;
    }

    @Override
    public GetCarResponse getCar(GetCarQuery command) {
        //TODO implement
        return null;
    }

    @Override
    public ReturnCarResponse returnCar(ReturnCarCommand command) {
        //TODO implement
        return null;
    }
}
