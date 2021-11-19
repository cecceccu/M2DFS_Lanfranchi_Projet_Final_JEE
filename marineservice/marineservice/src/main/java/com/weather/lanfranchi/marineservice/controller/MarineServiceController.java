package com.weather.lanfranchi.marineservice.controller;

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


@RestController

@Api(value = "Gets marine weather based on coordinates")
public class MarineServiceController {

    @Autowired
    RestTemplate restTemplate;

    private String marineWeatherToJSON(String response)
    {
        JSONObject jsonResponse = new JSONObject();


        int windSpeed;
        int waveHeight;
        int swellHeight;


        try {

            JSONObject json = new JSONObject(response);

            windSpeed = json.getInt("windSpeed");
            waveHeight = json.getInt("waveHeight");
            swellHeight = json.getInt("swellHeight");

            jsonResponse.put("Wind speed", windSpeed);
            jsonResponse.put("Wave height", waveHeight);
            jsonResponse.put("Swell height", swellHeight);


        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }


        return jsonResponse.toString();
    }




    @ApiOperation(value = "Get marine weather for coordinates", response = Iterable.class, tags = "marineWeather")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
    })
    @RequestMapping(value = "/marineWeather/{lng}/{lat}", method = RequestMethod.GET)
    public String marineWeather(@PathVariable int lng, @PathVariable int lat)
    {

        String response = restTemplate.exchange("http://marine-weather/getMarineWeather/{lng}/{lat}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, lng, lat ).getBody();


        return marineWeatherToJSON(response);
    }




    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
