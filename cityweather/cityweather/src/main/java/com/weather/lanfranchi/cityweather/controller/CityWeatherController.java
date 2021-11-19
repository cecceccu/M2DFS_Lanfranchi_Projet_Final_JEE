package com.weather.lanfranchi.cityweather.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.json.JSONObject;
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

import java.util.HashMap;
@Api
@RestController
public class CityWeatherController {

    JSONObject jsonResponse;

    @Autowired
    RestTemplate restTemplate;


    public int getCityCode(String cityname)
    {
        int cityCode = -1;
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=28CM9CzJYA10F47nHbU7R7rTggEa3OCm&q={cityname}&language=fr-fr&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, cityname).getBody();

        // Remove leading and trailing [] to get a proper JSON
        response = response.substring(1, response.length() -1);

        try {
            jsonResponse = new JSONObject(response);
            cityCode = jsonResponse.getInt("Key");
        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }

        System.out.println(cityCode);
        return cityCode;
    }


    @ApiOperation(value = "Get current weather by ", response = Iterable.class, tags = "getStudents")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found")
    })
    @RequestMapping(value = "getCurrentWeatherByCode/{code}", method = RequestMethod.GET)
    public String getWeatherByCode(@PathVariable int code)
    {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{code}?apikey=28CM9CzJYA10F47nHbU7R7rTggEa3OCm&language=fr-fr&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;
    }

    @RequestMapping(value = "getCurrentWeatherByName/{cityname}", method = RequestMethod.GET)
    public String getWeatherByCode(@PathVariable String cityname)
    {
        int code = this.getCityCode(cityname);

        String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{code}?apikey=28CM9CzJYA10F47nHbU7R7rTggEa3OCm&language=fr-fr&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;
    }

    @RequestMapping(value = "get1DayDailyForecasts/{cityname}", method = RequestMethod.GET)
    public String get1DayDailyForecasts(@PathVariable String cityname)
    {
        int code = this.getCityCode(cityname);
        String response = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/1day/{code}?apikey=28CM9CzJYA10F47nHbU7R7rTggEa3OCm&language=en-us&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;

    }

    @RequestMapping(value = "get5DayDailyForecasts/{cityname}", method = RequestMethod.GET)
    public String get5DayDailyForecasts(@PathVariable String cityname)
    {
        int code = this.getCityCode(cityname);
        String response = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/5day/{code}?apikey=28CM9CzJYA10F47nHbU7R7rTggEa3OCm&language=en-us&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;

    }






    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
