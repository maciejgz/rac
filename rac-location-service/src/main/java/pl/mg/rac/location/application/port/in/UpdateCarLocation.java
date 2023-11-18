package pl.mg.rac.location.application.port.in;

import pl.mg.rac.location.application.dto.command.UpdateCarLocationCommand;
import pl.mg.rac.location.application.dto.exception.LocationUpdateException;

public interface UpdateCarLocation {

    void updateCarLocation(UpdateCarLocationCommand command) throws LocationUpdateException;

}
