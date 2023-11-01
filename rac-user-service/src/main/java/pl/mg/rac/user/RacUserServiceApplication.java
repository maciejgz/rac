package pl.mg.rac.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class RacUserServiceApplication {

    @Value("${rac.user.variable}")
    private String value;

    public static void main(String[] args) {
        SpringApplication.run(RacUserServiceApplication.class, args);
    }

    @Bean
    public void testing() {
        log.debug("Value: " + value);
    }

}
