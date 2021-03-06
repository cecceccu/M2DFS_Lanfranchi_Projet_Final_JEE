package com.weather.lanfranchi.alert.controller;


import com.weather.lanfranchi.alert.delegate.WeatherAlertDelegate;
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

@Api (value = "Get current weather alerts based on city name")
@RestController
public class WeatherAlertController {

    @Autowired
    WeatherAlertDelegate weatherAlertDelegate;

    @ApiOperation(value = "Get alert forecasts for 1 day by city name", response = Iterable.class, tags = "getAlertForecasts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "get1DayAlerts/{cityname}", method = RequestMethod.GET)
    public String get1DayWeatherAlerts(@PathVariable String cityname)
    {
        return weatherAlertDelegate.get1DayWeatherAlerts(cityname);
    }

    @ApiOperation(value = "Get alert forecasts for 5 days by city name", response = Iterable.class, tags = "getAlertForecasts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "get5DayAlerts/{cityname}", method = RequestMethod.GET)
    public String get5DayWeatherAlerts(@PathVariable String cityname)
    {
        return weatherAlertDelegate.get5DayWeatherAlerts(cityname);
    }
}
