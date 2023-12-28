package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.model.SimulationCar;
import pl.mg.rac.simulation.model.SimulationLocation;
import pl.mg.rac.simulation.model.SimulationRent;
import pl.mg.rac.simulation.model.SimulationUser;
import pl.mg.rac.simulation.service.client.CarServiceClient;
import pl.mg.rac.simulation.service.client.LocationServiceClient;
import pl.mg.rac.simulation.service.client.RentServiceClient;
import pl.mg.rac.simulation.service.client.UserServiceClient;
import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Optional;

@Component
@Slf4j
@RefreshScope
public class RentCarScenario implements SimulationScenario {

    protected static final String RENT_ACCEPTED_RESPONSE_MESSAGE = "RENT_ACCEPTED";
    protected static final String RETURN_ACCEPTED_RESPONSE_MESSAGE = "RETURN_ACCEPTED";
    @Value("${rac.simulation.probability.rent-car-scenario}")
    private double probability;

    private final UserServiceClient userServiceClient;
    private final CarServiceClient carServiceClient;
    private final RentServiceClient rentServiceClient;
    private final LocationServiceClient locationServiceClient;

    public RentCarScenario(UserServiceClient userServiceClient, CarServiceClient carServiceClient,
                           RentServiceClient rentServiceClient, LocationServiceClient locationServiceClient) {
        this.userServiceClient = userServiceClient;
        this.carServiceClient = carServiceClient;
        this.rentServiceClient = rentServiceClient;
        this.locationServiceClient = locationServiceClient;
    }

    @Override
    public SimulationResult execute(int id) {
        log.debug("SCENARIO: RentCarScenario");
        try {
            Optional<SimulationUser> randomUser = userServiceClient.getRandomUser();
            Optional<SimulationCar> randomCar = carServiceClient.getRandomCar();
            Optional<SimulationRent> rentOpt;
            if (randomUser.isPresent() && randomCar.isPresent()) {
                SimulationResult result = null;
                if (StringUtils.isNotBlank(randomCar.get().getRentalId()) || StringUtils.isNotBlank(randomUser.get().getCurrentRentId())) {
                    log.debug("execute() RentCarScenario randomCar is rented or randomUser is renting");
                    result = new SimulationResult(RentCarScenario.class.getName(), false,
                            "randomCar is rented or randomUser is renting");
                }
                rentOpt = rentServiceClient.rentCar(randomUser.get().getName(), randomCar.get().getVin(), randomCar.get().getLocation());
                if (rentOpt.isEmpty()) {
                    log.debug("execute() RentCarScenario cannot rent car");
                    result = new SimulationResult(RentCarScenario.class.getName(), false, "rent is empty");
                } else if (rentOpt.get().getStatus().equals("RENT_FAILED")) {
                    log.debug("execute() RentCarScenario cannot rent car");
                    result = new SimulationResult(RentCarScenario.class.getName(), false, "rent failed: " + rentOpt.get().getStatusReason());
                } else {

                    SimulationRent rent = rentOpt.get();
                    //wait for rent to be accepted
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(2000);
                        Optional<SimulationRent> rCheck = rentServiceClient.getRent(rent.getRentId());
                        if (rCheck.isPresent() && rCheck.get().getStatus().equals(RENT_ACCEPTED_RESPONSE_MESSAGE)) {
                            rent = rCheck.get();
                            break;
                        }
                    }

                    if (rent.getStatus().equals(RENT_ACCEPTED_RESPONSE_MESSAGE)) {
                        log.debug("RentCarScenario rent {} accepted", rent.getRentId());
                    } else {
                        log.debug("RentCarScenario rent {} not accepted", rent.getRentId());
                        return new SimulationResult(RentCarScenario.class.getName(), false,
                                "RentCarScenario rent " + rent.getRentId() + " not accepted");
                    }
                    SecureRandom secureRandom = new SecureRandom();
                    //sent location updates with random probability - between 1 and 4 seconds
                    for (int i = 0; i < 4; i++) {
                        Thread.sleep((long) (secureRandom.nextDouble() * 3000 + 1000));
                        locationServiceClient.publishCarLocation(randomCar.get().getVin(),
                                SimulationLocation.randomOfOtherLocation(randomCar.get().getLocation(), 2));
                    }
                    //return car request
                    SimulationResult simulationResult = rentServiceClient.returnCar(rent.getRentId());
                    if (!simulationResult.success()) {
                        log.debug("execute() RentCarScenario returnCar failed");
                        result = simulationResult;
                    }
                    String lastReturnStatus = null;
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(2000);
                        Optional<SimulationRent> returnCheck = rentServiceClient.getRent(rent.getRentId());
                        if (returnCheck.isPresent() && returnCheck.get().getStatus().equals(RETURN_ACCEPTED_RESPONSE_MESSAGE)) {
                            result = new SimulationResult(RentCarScenario.class.getName(), true, "RentCarScenario rent " + rent.getRentId() + " finished");
                            break;
                        } else if (returnCheck.isPresent()) {
                            lastReturnStatus = returnCheck.get().getStatus();
                        }
                    }
                    if (result == null) {
                        log.debug("execute() RentCarScenario rent {} not finished", rent.getRentId());
                        result = new SimulationResult(RentCarScenario.class.getName(), false,
                                "RentCarScenario rent " + rent.getRentId() + " finished with status " + lastReturnStatus);
                    }
                }
                return result;
            } else {
                log.debug("execute() RentCarScenario randomUser or randomCar is null");
                if (randomUser.isEmpty()) {
                    log.debug("execute() RentCarScenario randomUser is empty");
                    return new SimulationResult(RentCarScenario.class.getName(), false, "randomUser is empty");
                } else {
                    log.debug("execute() RentCarScenario randomCar is empty");
                    return new SimulationResult(RentCarScenario.class.getName(), false, "randomCar is empty");
                }
            }
        } catch (InterruptedException | IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
            return new SimulationResult(RentCarScenario.class.getName(), false, e.getMessage());
        }
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
