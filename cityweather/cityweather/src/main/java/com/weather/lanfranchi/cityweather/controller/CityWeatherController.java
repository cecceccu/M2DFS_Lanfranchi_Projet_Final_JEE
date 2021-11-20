package com.weather.lanfranchi.cityweather.controller;

import com.weather.lanfranchi.cityweather.delegate.CityWeatherDelegate;
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
@Api (value = "Get current weather or forecast based on city name")
@RestController
public class CityWeatherController {
    @Autowired
    CityWeatherDelegate cityWeatherDelegate;


    @ApiOperation(value = "Get current weather by city code", response = Iterable.class, tags = "getCurrentWeather")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "getCurrentWeatherByCode/{code}", method = RequestMethod.GET)
    public String getWeatherByCode(@PathVariable int code)
    {
        return cityWeatherDelegate.getWeatherByCode(code);
    }

    @ApiOperation(value = "Get current weather by city name", response = Iterable.class, tags = "getCurrentWeather")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "getCurrentWeatherByName/{cityname}", method = RequestMethod.GET)
    public String getWeatherByName(@PathVariable String cityname)
    {
        return cityWeatherDelegate.getWeatherByName(cityname);
    }

    @ApiOperation(value = "Get 1 day of weather forecasts for city", response = Iterable.class, tags = "getWeatherForecasts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "get1DayDailyForecasts/{cityname}", method = RequestMethod.GET)
    public String get1DayDailyForecasts(@PathVariable String cityname)
    {
         return cityWeatherDelegate.get1DayDailyForecasts(cityname);
    }


    @ApiOperation(value = "Get 5 days of weather forecasts for city", response = Iterable.class, tags = "getWeatherForecasts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "get5DayDailyForecasts/{cityname}", method = RequestMethod.GET)
    public String get5DayDailyForecasts(@PathVariable String cityname)
    {
        return cityWeatherDelegate.get5DayDailyForecasts(cityname);
    }

    //10 day and 15 days forecasts are not available with a free API key



}
