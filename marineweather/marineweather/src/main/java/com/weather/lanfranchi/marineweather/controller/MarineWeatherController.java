package com.weather.lanfranchi.marineweather.controller;


import com.weather.lanfranchi.marineweather.model.MarineWeather;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MarineWeatherController {


    //Static DB because API is broken


    private static Map<String, List<MarineWeather>> weatherData = new HashMap<String, List<MarineWeather>>();


    //create static db
    static {

        List<MarineWeather> weatherList = new ArrayList<MarineWeather>();
        MarineWeather weather = new MarineWeather(50, 133, 50);
        weatherList.add(weather);
        weather = new MarineWeather(40, 50, 25);
        weatherList.add(weather);
        weather = new MarineWeather(33, 33, 33);
        weatherList.add(weather);
        weatherData.put("Ajaccio", weatherList);

        weatherList = new ArrayList<MarineWeather>();
        weather = new MarineWeather(20, 13, 40);
        weatherList.add(weather);
        weather = new MarineWeather(400, 5, 45);
        weatherList.add(weather);
        weather = new MarineWeather(133, 63, 43);
        weatherList.add(weather);
        weatherData.put("London", weatherList);

    }

    @RequestMapping(value = "getMarineWeather/{cityname}", method = RequestMethod.GET)
    public MarineWeather getWeather(@PathVariable String cityname) {


        List<MarineWeather> weather = weatherData.get(cityname);


        return weather!=null?weather.get(0):new MarineWeather(404, 404, 404);

    }
}
