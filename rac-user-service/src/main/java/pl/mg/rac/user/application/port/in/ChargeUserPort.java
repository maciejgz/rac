package pl.mg.rac.user.application.port.in;

import pl.mg.rac.user.application.dto.command.ChargeUserCommand;
import pl.mg.rac.user.application.dto.exception.UserChargeException;
import pl.mg.rac.user.application.dto.response.ChargeUserResponse;

public interface ChargeUserPort {

    ChargeUserResponse chargeUser(ChargeUserCommand command) throws UserChargeException;
}
