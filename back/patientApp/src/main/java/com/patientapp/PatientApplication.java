package com.patientapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients("com.patientapp")
@EnableSwagger2
@SpringBootApplication
public class PatientApplication{
	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

}
