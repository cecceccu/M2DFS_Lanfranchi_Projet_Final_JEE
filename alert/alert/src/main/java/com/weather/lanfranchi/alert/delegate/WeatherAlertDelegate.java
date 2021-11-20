package com.weather.lanfranchi.alert.delegate;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherAlertDelegate {
    private String apiKey = "90s9YJcEqa7CAIm8N4vBdQOm6xuK0Ry7";

    @Autowired
    RestTemplate restTemplate;

    public int getCityCode(String cityname)
    {
        JSONObject jsonResponse;

        int cityCode = -1;
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey="+ this.apiKey + "&q={cityname}&language=fr-fr&details=false",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, cityname).getBody();

        // Remove leading and trailing [] to get a proper JSON
        response = response.substring(1, response.length() -1);

        try {
            jsonResponse = new JSONObject(response);
            cityCode = jsonResponse.getInt("Key");
        }catch (JSONException err){
            System.out.println("There was an errror while retrieving data");
        }
        return cityCode;
    }

    @HystrixCommand(fallbackMethod = "weatherAlertApiErrorFallback")
    public String get1DayWeatherAlerts(String cityname)
    {
        int code = this.getCityCode(cityname);
        String response = restTemplate.exchange("http://dataservice.accuweather.com/alarms/v1/1day/{code}?apikey="+ this.apiKey,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;
    }

    @HystrixCommand(fallbackMethod = "weatherAlertApiErrorFallback")
    public String get5DayWeatherAlerts(String cityname)
    {
        int code = this.getCityCode(cityname);
        String response = restTemplate.exchange("http://dataservice.accuweather.com/alarms/v1/1day/{code}?apikey="+ this.apiKey,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, code).getBody();
        return response;
    }

    private String weatherAlertApiErrorFallback(String cityname)
    {
        return "Circuit breaker open, displaying default weather alerts : {Alarms:[Rain, Sustained Wind]}";
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
