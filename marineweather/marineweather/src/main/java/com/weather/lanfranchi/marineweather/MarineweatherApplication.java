package com.weather.lanfranchi.marineweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication

@EnableEurekaClient
public class MarineweatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarineweatherApplication.class, args);
	}

}
