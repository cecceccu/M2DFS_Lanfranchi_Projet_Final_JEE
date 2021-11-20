package com.weather.lanfranchi.marineservice.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class MarineServiceDelegate {

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

            jsonResponse.put("Wind speed", windSpeed + " km/h");
            jsonResponse.put("Wave height", waveHeight + " cm");
            jsonResponse.put("Swell height", swellHeight + " cm");


        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }


        return jsonResponse.toString();
    }

    @HystrixCommand(fallbackMethod = "marineWeatherApiErrorFallbackService")
    public String marineWeather(int lng, int lat)
    {

        String response = restTemplate.exchange("http://marine-weather/getMarineWeather/{lng}/{lat}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, lng, lat ).getBody();


        return marineWeatherToJSON(response);
    }


    //This is a test endpoint to check if ribbon loadBalancing works, it should be removed
    //in production, but I let it here to make your testing easier
    @HystrixCommand(fallbackMethod = "portGetErrorFallbackService")
    public String marineWeatherPort()
    {
        return restTemplate.exchange("http://marine-weather/marineWeather/getPort",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, (Object) null).getBody();
    }

    private String marineWeatherApiErrorFallbackService(int lng, int  lat)
    {
        return "Circuit breaker open, displaying default marine weather : {" +
                "Wind speed : 10 km/h, Wave Height : 60cm, Swell Height : 40cm}";
    }

    private String portGetErrorFallbackService()
    {
        return "Circuit breaker open, could not retrieve port of server, here is a placeholder" +
                "instead : 1234";
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }


}
