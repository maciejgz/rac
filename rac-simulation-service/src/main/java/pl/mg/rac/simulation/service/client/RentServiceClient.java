package pl.mg.rac.simulation.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mg.rac.simulation.model.SimulationLocation;
import pl.mg.rac.simulation.model.SimulationRent;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class RentServiceClient implements ServiceClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public SimulationRent rentCar(String username, String vin, SimulationLocation location) throws IOException, URISyntaxException, InterruptedException {
        log.debug("rentCar() called with: username = [" + username + "], vin = [" + vin + "], location = [" + location + "]");
        RentCarCommand command = new RentCarCommand(username, vin, location);

        ObjectMapper objectMapper = new ObjectMapper();
        String commandJson = objectMapper.writeValueAsString(command);

        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/rent"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(commandJson))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        //map response to SimulationRent
        SimulationRent rent = objectMapper.readValue(response.body(), SimulationRent.class);
        log.debug("status code: " + response.statusCode());
        log.debug(response.body());
        return rent;
    }

    public void returnCar(String rentId) throws IOException, URISyntaxException, InterruptedException {
        log.debug("returnCar() called with: rentId = [" + rentId + "]");

        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/return/" + rentId))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("status code: " + response.statusCode());
        log.debug(response.body());
    }

    private record RentCarCommand(String username, String vin, SimulationLocation location) {
    }



}
