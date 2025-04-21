package tn.esprit.arctic.lawappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class LawappbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LawappbackendApplication.class, args);
	}

}
