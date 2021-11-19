package com.weather.lanfranchi.cityweatherservice.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
public class CityWeatherServiceController {

    @Autowired
    RestTemplate restTemplate;


    private JSONObject currentWeatherResponseToJSON(String response)
    {
        JSONObject jsonResponse = new JSONObject();

        String dateTime;
        String weatherText;
        Boolean hasPrecipitation;
        int temperature;

        // Remove leading and trailing [] to get a proper JSON
        response = response.substring(1, response.length() -1);

        try {
            JSONObject json = new JSONObject(response);
            dateTime = json.getString("LocalObservationDateTime");
            weatherText = json.getString("WeatherText");
            hasPrecipitation = json.getBoolean("HasPrecipitation");
            temperature = json.getJSONObject("Temperature").getJSONObject("Metric").getInt("Value");
            jsonResponse.put("date", dateTime);
            jsonResponse.put("weather", weatherText);
            jsonResponse.put("precipitations", hasPrecipitation);
            jsonResponse.put("temperature", temperature + "°C");

        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }

        return jsonResponse;
    }

    private JSONObject forecastsToJSON(String response, int days)
    {
        JSONObject jsonResponse = new JSONObject();

        String dateTime;
        int minTemp;
        int maxTemp;
        String dayWeather;
        String nightWeather;

            try {
                JSONObject json = new JSONObject(response);
                JSONArray array  = json.getJSONArray("DailyForecasts");
                System.out.println("a");

                for (int i=0; i<days; i++)
                {
                    json = array.getJSONObject(i);
                    System.out.println(json.toString());
                    dateTime = json.getString("Date");
                    minTemp = json.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
                    maxTemp = json.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
                    dayWeather = json.getJSONObject("Day").getString("IconPhrase");
                    nightWeather = json.getJSONObject("Night").getString("IconPhrase");

                    JSONObject weatherDataForDay = new JSONObject();
                    weatherDataForDay.put("Minimal temperature", minTemp);
                    weatherDataForDay.put("Maximal temperature", maxTemp);
                    weatherDataForDay.put("Weather forecast for the day", dayWeather);
                    weatherDataForDay.put("Weather forecast for the night", nightWeather);

                    jsonResponse.put(dateTime, weatherDataForDay);
                }



            }catch (JSONException err){
                System.out.println("There was an errror while retrieving data");
            }


        return jsonResponse;
    }


    @RequestMapping(value = "/currentWeather/{cityname}", method = RequestMethod.GET)

    public String currentWeather(@PathVariable String cityname)
    {

        String response = restTemplate.exchange("http://current-forecasts/getCurrentWeatherByName/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return currentWeatherResponseToJSON(response).toString();
    }

    @RequestMapping(value = "/currentWeatherByCode/{citycode}", method = RequestMethod.GET)

    public String currentWeatherByCode(@PathVariable int citycode)
    {

        String response = restTemplate.exchange("http://current-forecasts/getCurrentWeatherByCode/{citycode}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},citycode ).getBody();


        return currentWeatherResponseToJSON(response).toString();
    }

    @RequestMapping(value = "/1DayForecasts/{cityname}", method = RequestMethod.GET)

    public String oneDayForecasts(@PathVariable String cityname)
    {

        String response = restTemplate.exchange("http://current-forecasts/get1DayDailyForecasts/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return forecastsToJSON(response, 1).toString();
    }

    @RequestMapping(value = "/5DayForecasts/{cityname}", method = RequestMethod.GET)

    public String fiveDayForecasts(@PathVariable String cityname)
    {

        String response = restTemplate.exchange("http://current-forecasts/get5DayDailyForecasts/{cityname}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {},cityname ).getBody();


        return forecastsToJSON(response, 5).toString();
    }




    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
