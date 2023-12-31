package pl.mg.rac.car.application.facade;

import pl.mg.rac.car.application.dto.command.*;
import pl.mg.rac.car.application.dto.exception.CarAlreadyExistsException;
import pl.mg.rac.car.application.dto.exception.CarAlreadyNotExistException;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.exception.CarRentedException;
import pl.mg.rac.car.application.dto.query.GetCarQuery;
import pl.mg.rac.car.application.dto.response.*;
import pl.mg.rac.car.application.port.in.*;
import pl.mg.rac.car.application.service.EventApplicationService;
import pl.mg.rac.commons.event.RacEvent;

public class CarFacade {

    private final AddCar addCarAdapter;
    private final DeleteCar deleteCarAdapter;
    private final RentCar rentCarAdapter;
    private final ReturnCar returnCarAdapter;
    private final GetCar getCarAdapter;
    private final GetRandomCar getRandomCarAdapter;
    private final EventApplicationService eventApplicationService;

    private final ReportCarFailurePort reportCarFailurePort;
    private final ReportCarFailureFixPort reportCarFailureFixPort;

    public CarFacade(AddCar addCarAdapter, DeleteCar deleteCarAdapter, RentCar rentCarAdapter, ReturnCar returnCarAdapter, GetCar getCarAdapter, GetRandomCar getRandomCarAdapter, EventApplicationService eventApplicationService, ReportCarFailurePort reportCarFailurePort, ReportCarFailureFixPort reportCarFailureFixPort) {
        this.addCarAdapter = addCarAdapter;
        this.deleteCarAdapter = deleteCarAdapter;
        this.rentCarAdapter = rentCarAdapter;
        this.returnCarAdapter = returnCarAdapter;
        this.getCarAdapter = getCarAdapter;
        this.getRandomCarAdapter = getRandomCarAdapter;
        this.eventApplicationService = eventApplicationService;
        this.reportCarFailurePort = reportCarFailurePort;
        this.reportCarFailureFixPort = reportCarFailureFixPort;
    }

    public AddCarResponse addCar(AddCarCommand command) throws CarAlreadyExistsException {
        return addCarAdapter.addCar(command);
    }

    public DeleteCarResponse deleteCar(DeleteCarCommand command) throws CarAlreadyNotExistException, CarRentedException {
        return deleteCarAdapter.deleteCar(command);
    }


    public RentCarResponse rentCar(RentCarCommand command) throws CarNotFoundException {
        return rentCarAdapter.rentCar(command);
    }

    public ReturnCarResponse returnCar(ReturnCarCommand command) throws CarNotFoundException {
        return returnCarAdapter.returnCar(command);
    }

    public GetCarResponse getCar(GetCarQuery query) throws CarNotFoundException {
        return getCarAdapter.getCar(query);
    }

    public void handleIncomingEvent(RacEvent<?> event) {
        eventApplicationService.handleIncomingEvent(event);
    }

    public GetCarResponse getRandomCar() throws CarNotFoundException {
        return getRandomCarAdapter.getRandomCar();
    }

    public ReportCarFailureResponse reportCarFailure(ReportCarFailureCommand command) throws CarNotFoundException {
        return reportCarFailurePort.reportCarFailure(command);
    }

    public ReportCarFailureFixResponse reportCarFailureFix(ReportCarFailureFixCommand command) throws CarNotFoundException {
        return reportCarFailureFixPort.reportCarFailureFix(command);
    }
}
