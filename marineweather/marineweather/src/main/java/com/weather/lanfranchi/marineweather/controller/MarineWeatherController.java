package com.weather.lanfranchi.marineweather.controller;


import com.weather.lanfranchi.marineweather.model.Coordinates;
import com.weather.lanfranchi.marineweather.model.MarineWeather;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(value = "Get current weather or forecast based on city name")
@RestController
public class MarineWeatherController {


    //Static DB because API is broken


    private static Map<Coordinates, List<MarineWeather>> weatherData = new HashMap<Coordinates, List<MarineWeather>>();


    //create static db
    static {

        List<MarineWeather> weatherList = new ArrayList<MarineWeather>();
        MarineWeather weather = new MarineWeather(50, 133, 50);
        weatherList.add(weather);
        weatherData.put(new Coordinates(150,50), weatherList);

        weatherList = new ArrayList<MarineWeather>();
        weather = new MarineWeather(20, 13, 40);
        weatherList.add(weather);
        weatherData.put(new Coordinates(80, 80), weatherList);

    }

    @ApiOperation(value = "Get marine weather based on coordinates", response = Iterable.class, tags = "getMarineWeather")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "success | OK"),
            @ApiResponse(code = 401, message = "error | Unauthorized"),
            @ApiResponse(code = 403, message = "error | Forbidden"),
            @ApiResponse(code = 404, message = "error | Not found"),
            @ApiResponse(code = 500, message = "error | Internal server error (probably exceeded request limit)")
})
    @RequestMapping(value = "getMarineWeather/{lng}/{lat}", method = RequestMethod.GET)
    public MarineWeather getWeather(@PathVariable int lng, @PathVariable int lat) {


        List<MarineWeather> weather = null;
        List<Coordinates> coords = weatherData.keySet().stream().filter(x -> x.equals(new Coordinates(lng, lat)))
                .collect(Collectors.toList());

        if (!coords.isEmpty())
        {
            weather = weatherData.get(coords.get(0));
        }

        return weather!=null?weather.get(0):new MarineWeather(404, 404, 404);

    }
}
