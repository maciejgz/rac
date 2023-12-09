package pl.mg.rac.car.application.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.dto.command.*;
import pl.mg.rac.car.application.dto.exception.CarAlreadyExistsException;
import pl.mg.rac.car.application.dto.exception.CarAlreadyNotExistException;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.exception.CarRentedException;
import pl.mg.rac.car.application.dto.query.GetCarQuery;
import pl.mg.rac.car.application.dto.response.*;
import pl.mg.rac.car.application.port.in.*;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.domain.exception.CarAlreadyReturnedException;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.*;
import pl.mg.rac.commons.event.car.payload.*;

import java.util.Optional;

@Slf4j
public class CarApplicationService implements AddCar, DeleteCar, RentCar, ReturnCar, GetCar, GetRandomCar, ReportCarFailurePort, ReportCarFailureFixPort {

    private final CarDatabase carDatabase;
    private final CarEventPublisher eventPublisher;

    public CarApplicationService(CarDatabase carDatabase, CarEventPublisher eventPublisher) {
        this.carDatabase = carDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AddCarResponse addCar(AddCarCommand command) throws CarAlreadyExistsException {
        log.debug("addCar() called with: command = [" + command + "]");
        if (carDatabase.existsByVin(command.vin())) {
            throw new CarAlreadyExistsException("Car with vin: " + command.vin() + " already exists.");
        }
        Car car = new Car(command.vin(), command.location(), false, command.mileage(), null);
        Car saved = carDatabase.save(car);
        eventPublisher.publishCarEvent(new CarCreatedEvent(command.vin(), new CarCreatedPayload(command.vin(), command.location(), command.mileage())));
        return new AddCarResponse(saved.getVin(), saved.getLocation(), saved.getMileage());
    }

    @Override
    public DeleteCarResponse deleteCar(DeleteCarCommand command) throws CarAlreadyNotExistException, CarRentedException {
        log.debug("deleteCar() called with: command = [" + command + "]");

        if (!carDatabase.existsByVin(command.vin())) {
            throw new CarAlreadyNotExistException("Car with vin: " + command.vin() + " already not exist.");
        }
        Optional<Car> car = carDatabase.getCarByVin(command.vin());
        if (car.isPresent() && car.get().getRented()) {
            throw new CarRentedException("Car with vin: " + command.vin() + " is rented.");
        }
        carDatabase.deleteByVin(command.vin());
        eventPublisher.publishCarEvent(new CarDeletedEvent(command.vin(), new CarDeletedPayload(command.vin())));
        return new DeleteCarResponse(command.vin());
    }

    @Override
    public RentCarResponse rentCar(RentCarCommand command) throws CarNotFoundException {
        log.debug("rentCar() called with: command = [" + command + "]");
        Optional<Car> car = carDatabase.getCarByVin(command.vin());
        if (car.isPresent()) {
            car.get().rentCar(command.rentalId());
            carDatabase.save(car.get());
            for (RacEvent<?> event : car.get().getEvents()) {
                eventPublisher.publishCarEvent(event);
            }
            return new RentCarResponse(command.vin(), command.rentalId(), true);
        } else {
            CarRentFailedNotExistsEvent event = new CarRentFailedNotExistsEvent(command.vin(), new CarRentFailedNotExistsPayload(command.vin(), command.rentalId()));
            eventPublisher.publishCarEvent(event);
            throw new CarNotFoundException("Car with vin: " + command.vin() + " not found.");
        }
    }

    @Override
    public GetCarResponse getCar(GetCarQuery command) throws CarNotFoundException {
        Optional<Car> carByVin = carDatabase.getCarByVin(command.vin());
        return carByVin.map(car -> new GetCarResponse(car.getVin(), car.getLocation(), car.getRented(), car.getMileage(), car.getRentalId())).orElseThrow(
                () -> new CarNotFoundException("Car with vin: " + command.vin() + " not found.")
        );
    }

    @Override
    public ReturnCarResponse returnCar(ReturnCarCommand command) throws CarNotFoundException {
        log.debug("returnCar() called with: command = [" + command + "]");
        Optional<Car> car = carDatabase.getCarByVin(command.vin());
        if (car.isPresent()) {
            try {
                car.get().returnCar(command.rentalId(), command.distanceTraveled());
                carDatabase.save(car.get());
                for (RacEvent<?> event : car.get().getEvents()) {
                    eventPublisher.publishCarEvent(event);
                }
                return new ReturnCarResponse(command.vin(), command.distanceTraveled(), command.rentalId(), command.location(), true);
            } catch (CarAlreadyReturnedException e) {
                log.error("Car with vin: " + command.vin() + " is already rented. Rental id: " + car.get().getRentalId());
                eventPublisher.publishCarEvent(new CarReturnFailedAlreadyReturnedEvent(command.vin(),
                        new CarReturnFailedAlreadyReturnedPayload(command.vin(), command.rentalId(), command.location(), command.distanceTraveled())));
                return new ReturnCarResponse(command.vin(), command.distanceTraveled(), command.rentalId(), command.location(), false);
            }
        } else {
            eventPublisher.publishCarEvent(new CarReturnFailedNotExistsEvent(command.vin(),
                    new CarReturnFailedNotExistsPayload(command.vin(), command.rentalId(), command.location(), command.distanceTraveled())));
            throw new CarNotFoundException("Car with vin: " + command.vin() + " not found.");
        }
    }

    @Override
    public GetCarResponse getRandomCar() throws CarNotFoundException {
        log.debug("getRandomCar() called");
        Optional<Car> car = carDatabase.getRandomCar();
        if (car.isEmpty()) {
            throw new CarNotFoundException("No cars in database");
        } else {
            return new GetCarResponse(car.get().getVin(), car.get().getLocation(), car.get().getRented(), car.get().getMileage(), car.get().getRentalId());
        }
    }

    @Override
    public ReportCarFailureFixResponse reportCarFailureFix(ReportCarFailureFixCommand command) throws CarNotFoundException {
        log.debug("reportCarFailureFix() called with: command = [" + command + "]");
        Optional<Car> car = carDatabase.getCarByVin(command.vin());
        if (car.isPresent()) {
            car.get().removeFailure();
            carDatabase.save(car.get());
            return new ReportCarFailureFixResponse(command.vin(), car.get().getFailure());
        } else {
            throw new CarNotFoundException("Car with vin: " + command.vin() + " not found.");
        }
    }

    @Override
    public ReportCarFailureResponse reportCarFailure(ReportCarFailureCommand command) throws CarNotFoundException {
        log.debug("reportCarFailure() called with: command = [" + command + "]");
        Optional<Car> car = carDatabase.getCarByVin(command.vin());
        if (car.isPresent()) {
            car.get().setFailure(command.failureReason());
            carDatabase.save(car.get());
            return new ReportCarFailureResponse(command.vin(), car.get().getFailure(), car.get().getFailureReason());
        } else {
            throw new CarNotFoundException("Car with vin: " + command.vin() + " not found.");
        }
    }
}
