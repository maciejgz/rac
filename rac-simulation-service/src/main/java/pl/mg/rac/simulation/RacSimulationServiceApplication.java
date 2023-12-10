package pl.mg.rac.simulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RacSimulationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RacSimulationServiceApplication.class, args);
    }

}
