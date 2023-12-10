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

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Optional;

@Component
@Slf4j
@RefreshScope
public class RentCarScenario implements SimulationScenario {

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
    public void execute(int id) {
        log.debug("SCENARIO: RentCarScenario");
        try {
            Optional<SimulationUser> randomUser = userServiceClient.getRandomUser();
            Optional<SimulationCar> randomCar = carServiceClient.getRandomCar();
            Optional<SimulationRent> rentOpt;
            if (randomUser.isPresent() && randomCar.isPresent()) {
                if (StringUtils.isNotBlank(randomCar.get().getRentalId()) || StringUtils.isNotBlank(randomUser.get().getCurrentRentId())) {
                    log.debug("execute() RentCarScenario randomCar is rented or randomUser is renting");
                    return;
                }
                rentOpt = rentServiceClient.rentCar(randomUser.get().getName(), randomCar.get().getVin(), randomCar.get().getLocation());
                if (rentOpt.isEmpty()) {
                    log.debug("execute() RentCarScenario rent is empty");
                } else {
                    SimulationRent rent = rentOpt.get();
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(2000);
                        Optional<SimulationRent> rCheck = rentServiceClient.getRent(rent.getRentId());
                        if (rCheck.isPresent() && rCheck.get().getStatus().equals("RENT_ACCEPTED")) {
                            rent = rCheck.get();
                            break;
                        }
                    }

                    if (rent.getStatus().equals("RENT_ACCEPTED")) {
                        log.debug("RentCarScenario rent {} accepted", rent.getRentId());
                    } else {
                        log.debug("RentCarScenario rent {} not accepted", rent.getRentId());
                        return;
                    }
                    SecureRandom secureRandom = new SecureRandom();
                    //sent location updates with random probability - between 1 and 4 seconds
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep((long) (secureRandom.nextDouble() * 3000 + 1000));
                        locationServiceClient.publishCarLocation(randomCar.get().getVin(),
                                SimulationLocation.randomOfOtherLocation(randomCar.get().getLocation(), 2));
                    }
                    //return car
                    rentServiceClient.returnCar(rent.getRentId());
                }
            } else {
                log.debug("execute() RentCarScenario randomUser or randomCar is null");
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
