package pl.mg.rac.rent.application.port.in;

import pl.mg.rac.commons.value.Location;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;

public interface RequestRent {

    public RequestRentResponse requestRent(String username, String vin, Location location);

}
