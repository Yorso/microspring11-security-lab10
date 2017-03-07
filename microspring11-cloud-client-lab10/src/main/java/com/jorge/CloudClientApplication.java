package com.jorge;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CloudClientApplication {

	public static void main(String[] args) {
		//System.setProperty("spring.cloud.bootstrap.enabled","false");
		SpringApplication.run(CloudClientApplication.class, args);
	}
}
