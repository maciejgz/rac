package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.command.ReportCarFailureCommand;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.response.ReportCarFailureResponse;

public interface ReportCarFailurePort {

    ReportCarFailureResponse reportCarFailure(ReportCarFailureCommand command) throws CarNotFoundException;

}
