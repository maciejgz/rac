package pl.mg.rac.simulation.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mg.rac.simulation.model.SimulationUser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceClient implements ServiceClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public void registerUser(SimulationUser user) throws IOException, InterruptedException, URISyntaxException {
        log.debug("registerUser()");

        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(user);
        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(carJson))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("code: " + response.statusCode());
        log.debug(response.body());
    }

    public Optional<SimulationUser> getRandomUser() throws IOException, InterruptedException, URISyntaxException {
        log.debug("getRandomUser()");

        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/user/random"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("code: " + response.statusCode());
        log.debug(response.body());
        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.ofNullable(objectMapper.readValue(response.body(), SimulationUser.class));
        } else {
            return Optional.empty();
        }
    }

    public void deleteUser(String username) throws IOException, InterruptedException, URISyntaxException {
        log.debug("deleteUser()");

        //TODO use spring cloud feign client
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/user/" + username))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug("code: " + response.statusCode());
        log.debug(response.body());
    }

}
