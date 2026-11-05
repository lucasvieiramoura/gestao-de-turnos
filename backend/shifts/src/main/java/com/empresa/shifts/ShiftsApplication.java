package com.empresa.shifts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
@ConfigurationProperties 
public class ShiftsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiftsApplication.class, args);
	}

}
