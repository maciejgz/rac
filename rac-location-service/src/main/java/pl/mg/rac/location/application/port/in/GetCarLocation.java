package pl.mg.rac.location.application.port.in;

import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.dto.exception.LocationNotFoundException;

public interface GetCarLocation {

    public Location getCarLocation(String vin) throws LocationNotFoundException;

}
