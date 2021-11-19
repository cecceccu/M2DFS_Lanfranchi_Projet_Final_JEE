package com.weather.lanfranchi.marineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication

@EnableEurekaClient
public class MarineserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarineserviceApplication.class, args);
	}

}
