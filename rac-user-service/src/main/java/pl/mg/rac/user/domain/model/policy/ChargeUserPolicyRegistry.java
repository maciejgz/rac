package pl.mg.rac.user.domain.model.policy;

import pl.mg.rac.user.domain.model.User;

import java.time.LocalDate;

public class ChargeUserPolicyRegistry {

    public static ChargeUserPolicy getChargeUserPolicy(User user) {
        if (user.getRegistrationDate().isAfter(LocalDate.now().minusYears(1))) {
            return new FreshUserChargePolicy();
        } else {
            return new OldUserChargePolicy();
        }
    }

}
