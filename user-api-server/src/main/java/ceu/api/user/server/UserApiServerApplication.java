package ceu.api.user.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ceu")
@EntityScan("ceu")
@SpringBootApplication
public class UserApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApiServerApplication.class, args);
	}

}
