package pl.mg.rac.user.integration.infrastructure.in.web.controller;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.mg.rac.user.application.dto.response.UserResponse;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.infrastructure.out.messaging.KafkaTopicConfig;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserCommandTest {

    @LocalServerPort
    private int port;

    @Container
    public static KafkaContainer kafka = new KafkaContainer();

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.4");

    @Autowired
    UserDatabase userDatabase;

    @Autowired
    private TestRestTemplate restTemplate;

    //kafka
    private Consumer<String, Object> consumer;

    @DynamicPropertySource
    public static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setUp() {
        // Create a Kafka consumer
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(kafka.getBootstrapServers(), "testGroup", "true");
        consumer = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), new JsonDeserializer<>()).createConsumer();
        consumer.subscribe(Collections.singletonList(KafkaTopicConfig.RAC_USER_TOPIC)); // Replace with your topic name
    }

    @AfterEach
    void tearDown() {
        // Close the consumer and the embedded Kafka broker
        consumer.close();
    }

    @Test
    public void registerUserTest() {
        // Consume the message from Kafka in a separate thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Queue<String>> futureEvents = executorService.submit(kafkaClientTask);

        //create user in user-service
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String body = """
                {
                    "name" : "1s5sas",
                    "location" : {
                        "latitude": 1.2,
                        "longitude": 0.1
                    }
                }""";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/user", HttpMethod.POST,
                requestEntity, String.class);

        //check if user was created
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // check if user was created in database
        assertTrue(userDatabase.exists("1s5sas"));

        //check if user was created in API
        ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity("http://localhost:" + port + "/user/1s5sas", UserResponse.class);
        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        assertNotNull(userResponse.getBody());
        assertEquals("1s5sas", userResponse.getBody().name());

        //check if user was created in Kafka
        try {
            Queue<String> events = futureEvents.get();
            assertEquals(1, events.size());
            // Now 'events' contains the list of events generated
        } catch (InterruptedException | ExecutionException ignored) {
        }
        executorService.shutdown();
    }

    Callable<Queue<String>> kafkaClientTask = () -> {
        Queue<String> events = new ConcurrentLinkedQueue<>();
        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(10));
        records.forEach(record -> events.add(record.value().toString()));
        return events;
    };

}