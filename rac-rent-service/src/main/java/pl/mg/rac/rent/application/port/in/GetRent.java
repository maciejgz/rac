package pl.mg.rac.rent.application.port.in;

import pl.mg.rac.rent.application.dto.exception.RentNotFoundException;
import pl.mg.rac.rent.application.dto.query.GetRentByIdQuery;
import pl.mg.rac.rent.application.dto.response.RentResponse;

public interface GetRent {

    RentResponse getRent(GetRentByIdQuery query) throws RentNotFoundException;

}
