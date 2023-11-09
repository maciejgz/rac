package pl.mg.rac.car.application.service;

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
}
