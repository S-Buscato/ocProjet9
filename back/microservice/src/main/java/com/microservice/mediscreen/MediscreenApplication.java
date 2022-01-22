package com.microservice.mediscreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MediscreenApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenApplication.class, args);
	}

}
