package uz.zinnur.cleaning_carpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class CleaningCarpetApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleaningCarpetApplication.class, args);
    }

}
