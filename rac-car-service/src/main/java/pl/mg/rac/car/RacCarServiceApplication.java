package pl.mg.rac.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RacCarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RacCarServiceApplication.class, args);
    }

}
