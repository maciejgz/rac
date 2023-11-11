package pl.mg.rac.car.application.service;

import lombok.extern.slf4j.Slf4j;
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
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.domain.model.Car;

import java.util.Optional;

@Slf4j
public class CarApplicationService implements AddCar, DeleteCar, RentCar, ReturnCar, GetCar {

    private final CarDatabase carDatabase;
    private final CarEventPublisher messageBroker;

    public CarApplicationService(CarDatabase carDatabase, CarEventPublisher messageBroker) {
        this.carDatabase = carDatabase;
        this.messageBroker = messageBroker;
    }

    @Override
    public AddCarResponse addCar(AddCarCommand command) throws CarAlreadyExistsException {
        log.debug("addCar() called with: command = [" + command + "]");
        if (carDatabase.existsByVin(command.vin())) {
            throw new CarAlreadyExistsException("Car with vin: " + command.vin() + " already exists.");
        }
        Car car = new Car(command.vin(), command.location(), false, command.mileage(), null);
        Car saved = carDatabase.save(car);
        //TODO publish event
        return new AddCarResponse(saved.getVin(), saved.getLocation(), saved.getMileage());
    }

    @Override
    public DeleteCarResponse deleteCar(DeleteCarCommand command) throws CarAlreadyNotExistException {
        log.debug("deleteCar() called with: command = [" + command + "]");
        if (!carDatabase.existsByVin(command.vin())) {
            throw new CarAlreadyNotExistException("Car with vin: " + command.vin() + " already not exist.");
        }
        carDatabase.deleteByVin(command.vin());
        //TODO publish event
        return new DeleteCarResponse(command.vin());
    }

    @Override
    public RentCarResponse rentCar(RentCarCommand command) throws CarNotFoundException {
        Optional<Car> car = carDatabase.getCarByVin(command.vin());
        if (car.isPresent()) {
            car.get().rentCar(command.rentalId());
        } else {
            throw new CarNotFoundException("Car with vin: " + command.vin() + " not found.");
        }
        return null;
    }

    @Override
    public GetCarResponse getCar(GetCarQuery command) throws CarNotFoundException {
        Optional<Car> carByVin = carDatabase.getCarByVin(command.vin());
        return carByVin.map(car -> new GetCarResponse(car.getVin(), car.getLocation(), car.getRented(), car.getMileage(), car.getRentalId())).orElseThrow(
                () -> new CarNotFoundException("Car with vin: " + command.vin() + " not found.")
        );
    }

    @Override
    public ReturnCarResponse returnCar(ReturnCarCommand command) {
        //TODO implement
        return null;
    }
}
