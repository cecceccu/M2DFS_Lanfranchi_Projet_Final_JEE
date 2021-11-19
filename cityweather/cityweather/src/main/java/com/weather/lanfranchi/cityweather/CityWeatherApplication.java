package com.weather.lanfranchi.cityweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication

@EnableEurekaClient

public class CityWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityWeatherApplication.class, args);
	}

}
