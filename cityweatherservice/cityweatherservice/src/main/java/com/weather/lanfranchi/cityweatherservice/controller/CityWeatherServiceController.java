package com.weather.lanfranchi.cityweatherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CityWeatherServiceController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/currentWeather/{cityname}", method = RequestMethod.GET)
    public String currentWeather(@PathVariable String cityname)
    {

        String response = restTemplate.exchange("http://current-forecasts/getCurrentWeatherByName/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return response;
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
