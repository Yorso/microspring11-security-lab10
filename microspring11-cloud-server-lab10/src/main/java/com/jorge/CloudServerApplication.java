package com.jorge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/*
 * Try:
 * 		http://localhost:8001/microspring2-cloud-client-lab3/default/  => You should see the JSON result that will actually be used by Spring
 * 
 */
/**
 * What happens if the Config Server is unavailable when the “lucky word” application starts? To mitigate this possibility, it is common to run multiple instances of the config server in different racks / zones behind a load balancer.
 * What happens if we change a property after client applications have started? The server picks up the changes immediately, but the client does not. Later we will see how Spring Cloud Bus and refresh scope can be used to dynamically propagate changes.
 * 
 */
@SpringBootApplication
@EnableConfigServer
public class CloudServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudServerApplication.class, args);
	}
}
