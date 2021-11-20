package com.weather.lanfranchi.cityweatherservice.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.weather.lanfranchi.cityweatherservice.delegate.CityWeatherServiceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONArray;
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

@RestController
@Api(value = "Api to format current weather and forecasts responses")
public class CityWeatherServiceController {

    @Autowired
    CityWeatherServiceDelegate cityWeatherServiceDelegate;



    @ApiOperation(value = "Get current weather by city name", response = Iterable.class, tags = "currentWeather")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "/currentWeather/{cityname}", method = RequestMethod.GET)

    public String currentWeather(@PathVariable String cityname)
    {

        return cityWeatherServiceDelegate.currentWeather(cityname);
    }

    @ApiOperation(value = "Get current weather by city code", response = Iterable.class, tags = "currentWeather")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "/currentWeatherByCode/{citycode}", method = RequestMethod.GET)

    public String currentWeatherByCode(@PathVariable int citycode)
    {

        return cityWeatherServiceDelegate.currentWeatherByCode(citycode);
    }

    @RequestMapping(value = "/1DayForecasts/{cityname}", method = RequestMethod.GET)


    @ApiOperation(value = "Get 1 day forecasts for city", response = Iterable.class, tags = "forecasts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    public String oneDayForecasts(@PathVariable String cityname)
    {

        return cityWeatherServiceDelegate.oneDayForecasts(cityname);
    }

    @ApiOperation(value = "Get 5 day forecasts for city", response = Iterable.class, tags = "forecasts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "/5DayForecasts/{cityname}", method = RequestMethod.GET)

    public String fiveDayForecasts(@PathVariable String cityname)
    {

        return cityWeatherServiceDelegate.fiveDayForecasts(cityname);
    }
}
