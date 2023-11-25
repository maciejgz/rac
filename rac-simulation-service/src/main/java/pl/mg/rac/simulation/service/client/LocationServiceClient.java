package pl.mg.rac.simulation.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mg.rac.simulation.model.SimulationLocation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class LocationServiceClient implements ServiceClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public void publishUserLocation(String username, SimulationLocation location) throws IOException, InterruptedException, URISyntaxException {
        log.info("Publishing user location: {} {}", username, location);

        UpdateUserLocationCommand command = new UpdateUserLocationCommand(username, location);
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(command);
        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/location/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(carJson))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("status code: " + response.statusCode());
        log.debug(response.body());
    }

    public void publishCarLocation(String vin, SimulationLocation location) throws IOException, InterruptedException, URISyntaxException {
        log.info("Publishing user location: {} {}", vin, location);

        UpdateCarLocationCommand command = new UpdateCarLocationCommand(vin, location);
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(command);
        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/location/car"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(carJson))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("code: " + response.statusCode());
        log.debug(response.body());
    }

    private record UpdateCarLocationCommand(String vin, SimulationLocation location) {
    }

    private record UpdateUserLocationCommand(String username, SimulationLocation location) {
    }

}
