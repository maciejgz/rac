package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
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
    public void execute() {
        log.debug("SCENARIO: RentCarScenario");
        try {
            SimulationUser randomUser = userServiceClient.getRandomUser();
            SimulationCar randomCar = carServiceClient.getRandomCar();
            SimulationRent rent;
            if (randomUser != null && randomCar != null) {
                if (randomCar.isRented() || randomUser.isRenting()) {
                    log.debug("execute() RentCarScenario randomCar is rented or randomUser is renting");
                    return;
                }
                rent = rentServiceClient.rentCar(randomUser.getName(), randomCar.getVin(), randomCar.getLocation());
            } else {
                log.debug("execute() RentCarScenario randomUser or randomCar is null");
                return;
            }
            SecureRandom secureRandom = new SecureRandom();
            //sent location updates with random probability - between 1 and 4 seconds
            for (int i = 0; i < 10; i++) {
                Thread.sleep((long) (secureRandom.nextDouble() * 3000 + 1000));
                locationServiceClient.publishCarLocation(randomCar.getVin(),
                        SimulationLocation.randomOfOtherLocation(randomCar.getLocation(), 2));
            }
            //return car
            rentServiceClient.returnCar(rent.getRentId());
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