package pl.mg.rac.location.application.port.in;

import pl.mg.rac.commons.value.Location;

public interface GetCarLocation {

    public Location getCarLocation(String vin);

}
