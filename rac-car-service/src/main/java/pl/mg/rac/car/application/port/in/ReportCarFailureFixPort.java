package pl.mg.rac.car.application.port.in;

import pl.mg.rac.car.application.dto.command.ReportCarFailureFixCommand;
import pl.mg.rac.car.application.dto.exception.CarNotFoundException;
import pl.mg.rac.car.application.dto.response.ReportCarFailureFixResponse;

public interface ReportCarFailureFixPort {

    ReportCarFailureFixResponse reportCarFailureFix(ReportCarFailureFixCommand command) throws CarNotFoundException;

}
