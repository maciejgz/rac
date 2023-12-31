package pl.mg.rac.simulation.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.mg.rac.simulation.model.SimulationCar;
import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@Slf4j
public class CarServiceClient implements ServiceClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public SimulationResult addCar(SimulationCar car) throws URISyntaxException, IOException, InterruptedException {
        log.debug("addCar()");

        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(car);
        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/car"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(carJson))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("code: " + response.statusCode());
        log.debug(response.body());

        if (response.statusCode() == HttpStatus.CREATED.value()) {
            return new SimulationResult("AddCarScenario", true, "Car created: " + car.getVin());
        } else {
            return new SimulationResult("AddCarScenario", false, response.body());
        }
    }

    public Optional<SimulationCar> getRandomCar() throws IOException, InterruptedException, URISyntaxException {
        log.debug("getRandomCar()");

        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/car/random"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("code: " + response.statusCode());
        log.debug(response.body());
        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.ofNullable(objectMapper.readValue(response.body(), SimulationCar.class));
        } else {
            return Optional.empty();
        }
    }

    public SimulationResult deleteCar(String vin) throws URISyntaxException, IOException, InterruptedException {
        log.debug("deleteCar()");

        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/car/" + vin))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("code: " + response.statusCode());
        log.debug(response.body());
        if(response.statusCode() == HttpStatus.NO_CONTENT.value()) {
            return new SimulationResult("RemoveCarScenario", true, "Car deleted: " + vin);
        } else {
            return new SimulationResult("RemoveCarScenario", false, response.body());
        }
    }

}
