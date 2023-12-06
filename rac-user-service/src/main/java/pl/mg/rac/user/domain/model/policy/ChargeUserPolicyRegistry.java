package pl.mg.rac.user.domain.model.policy;

import pl.mg.rac.user.domain.model.User;

public class ChargeUserPolicyRegistry {

    public static ChargeUserPolicy getChargeUserPolicy(User user) {
        if (user.getRegistrationDate().isAfter(user.getRegistrationDate().minusYears(1))) {
            return new FreshUserChargePolicy();
        } else {
            return new OldUserChargePolicy();
        }
    }

}
