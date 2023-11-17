package pl.mg.rac.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories
public class RacLocationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RacLocationServiceApplication.class, args);
    }

}
